package com.sequenceiq.cloudbreak.converter.v4.stacks.cli;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable.ProviderParameterCalculator;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.StackV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.cluster.ClusterV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.cluster.sharedservice.SharedServiceV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.customdomain.CustomDomainSettingsV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.environment.EnvironmentSettingsV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.environment.placement.PlacementSettingsV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.image.ImageSettingsV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.instancegroup.InstanceGroupV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.network.NetworkV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.authentication.StackAuthenticationV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.tags.TagsV4Request;
import com.sequenceiq.cloudbreak.api.util.ConverterUtil;
import com.sequenceiq.cloudbreak.cloud.model.Image;
import com.sequenceiq.cloudbreak.cloud.model.StackTags;
import com.sequenceiq.cloudbreak.converter.AbstractConversionServiceAwareConverter;
import com.sequenceiq.cloudbreak.core.CloudbreakImageNotFoundException;
import com.sequenceiq.cloudbreak.domain.Recipe;
import com.sequenceiq.cloudbreak.domain.stack.Stack;
import com.sequenceiq.cloudbreak.domain.stack.cluster.Cluster;
import com.sequenceiq.cloudbreak.domain.stack.instance.InstanceGroup;
import com.sequenceiq.cloudbreak.service.ComponentConfigProvider;
import com.sequenceiq.cloudbreak.service.stack.StackService;

@Component
public class StackToStackV4RequestConverter extends AbstractConversionServiceAwareConverter<Stack, StackV4Request> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StackToStackV4RequestConverter.class);

    @Inject
    private ComponentConfigProvider componentConfigProvider;

    @Inject
    private StackService stackService;

    @Inject
    private ConverterUtil converterUtil;

    @Inject
    private ProviderParameterCalculator providerParameterCalculator;

    @Override
    public StackV4Request convert(Stack source) {
        StackV4Request stackV2Request = new StackV4Request();
        stackV2Request.setEnvironment(getEnvironment(source));
        stackV2Request.setCustomDomain(getCustomDomainSettings(source));
        stackV2Request.setFlexId(source.getFlexSubscription() == null ? null : source.getFlexSubscription().getId());
        providerParameterCalculator.to(new HashMap<>(source.getParameters()), stackV2Request);
        stackV2Request.setAuthentication(getConversionService().convert(source.getStackAuthentication(), StackAuthenticationV4Request.class));
        stackV2Request.setNetwork(getConversionService().convert(source.getNetwork(), NetworkV4Request.class));
        stackV2Request.setCluster(getConversionService().convert(source.getCluster(), ClusterV4Request.class));
        stackV2Request.setInstanceGroups(getInstanceGroups(source));
        prepareImage(source, stackV2Request);
        prepareTags(source, stackV2Request);
        prepareDatalakeRequest(source, stackV2Request);
        return stackV2Request;
    }

    private void prepareDatalakeRequest(Stack source, StackV4Request stackV2Request) {
        if (source.getDatalakeId() != null) {
            SharedServiceV4Request sharedServiceRequest = new SharedServiceV4Request();
            sharedServiceRequest.setSharedClusterName(stackService.get(source.getDatalakeId()).getName());
            stackV2Request.getCluster().setSharedService(sharedServiceRequest);
        }
    }

    private PlacementSettingsV4Request getPlacementSettings(String region, String availabilityZone) {
        PlacementSettingsV4Request ps = new PlacementSettingsV4Request();
        ps.setRegion(region);
        ps.setAvailabilityZone(availabilityZone);
        return ps;
    }

    private CustomDomainSettingsV4Request getCustomDomainSettings(Stack stack) {
        CustomDomainSettingsV4Request cd = new CustomDomainSettingsV4Request();
        cd.setDomainName(stack.getCustomDomain());
        cd.setHostname(stack.getCustomHostname());
        cd.setHostgroupNameAsHostname(stack.isHostgroupNameAsHostname());
        cd.setClusterNameAsSubdomain(stack.isClusterNameAsSubdomain());
        return cd;
    }

    private EnvironmentSettingsV4Request getEnvironment(Stack source) {
        EnvironmentSettingsV4Request environment = new EnvironmentSettingsV4Request();
        environment.setName("");
        if (source.getEnvironment() != null) {
            environment.setName(source.getEnvironment().getName());
        }
        if (source.getCredential() != null) {
            environment.setCredentialName(source.getCredential().getName());
        }
        environment.setPlacement(getPlacementSettings(source.getRegion(), source.getAvailabilityZone()));
        return environment;
    }

    private void prepareImage(Stack source, StackV4Request stackV2Request) {
        try {
            Image image = componentConfigProvider.getImage(source.getId());
            ImageSettingsV4Request is = new ImageSettingsV4Request();
            is.setId(Strings.isNullOrEmpty(image.getImageId()) ? "" : image.getImageId());
            is.setCatalog(Strings.isNullOrEmpty(image.getImageCatalogName()) ? "" : image.getImageCatalogName());
            stackV2Request.setImage(is);
        } catch (CloudbreakImageNotFoundException e) {
            LOGGER.info(e.toString());
        }
    }

    private List<InstanceGroupV4Request> getInstanceGroups(Stack stack) {
        List<InstanceGroupV4Request> ret = new ArrayList<>();
        for (InstanceGroup instanceGroup : stack.getInstanceGroups()) {
            InstanceGroupV4Request instanceGroupV2Request = getConversionService().convert(instanceGroup, InstanceGroupV4Request.class);
            collectInformationsFromActualHostgroup(stack.getCluster(), instanceGroup, instanceGroupV2Request);
            ret.add(instanceGroupV2Request);
        }
        return ret;
    }

    private void collectInformationsFromActualHostgroup(Cluster cluster, InstanceGroup instanceGroup, InstanceGroupV4Request instanceGroupV2Request) {
        if (cluster != null && cluster.getHostGroups() != null) {
            cluster.getHostGroups().stream()
                    .filter(hostGroup -> hostGroup.getName().equals(instanceGroup.getGroupName()))
                    .findFirst()
                    .ifPresent(hostGroup -> {
                        Set<String> recipeNames = hostGroup.getRecipes().stream().map(Recipe::getName).collect(Collectors.toSet());
                        instanceGroupV2Request.setRecipeNames(recipeNames);
                        instanceGroupV2Request.setRecoveryMode(hostGroup.getRecoveryMode());
                        hostGroup.getHostMetadata().forEach(hmd -> {
                            instanceGroup.getInstanceMetaDataSet()
                                    .forEach(imd -> {
                                        if (hmd.getHostName().equals(imd.getDomain())) {

                                        }
                                    });
                        });
                    });
        }
    }

    private void prepareTags(Stack source, StackV4Request stackV2Request) {
        try {
            if (source.getTags() != null && source.getTags().getValue() != null) {
                StackTags stackTags = source.getTags().get(StackTags.class);
                if (stackTags.getUserDefinedTags() != null) {
                    TagsV4Request tags = new TagsV4Request();
                    tags.setApplication(null);
                    tags.setDefaults(null);
                    tags.setUserDefined(stackTags.getUserDefinedTags());
                    stackV2Request.setTags(tags);
                }
            }
        } catch (IOException e) {
            stackV2Request.setTags(null);
        }
    }
}
