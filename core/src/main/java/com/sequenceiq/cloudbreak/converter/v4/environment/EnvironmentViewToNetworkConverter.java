package com.sequenceiq.cloudbreak.converter.v4.environment;

import static com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable.CloudPlatform.AWS;
import static com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable.CloudPlatform.AZURE;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable.CloudPlatform;
import com.sequenceiq.cloudbreak.common.type.APIResourceType;
import com.sequenceiq.cloudbreak.converter.AbstractConversionServiceAwareConverter;
import com.sequenceiq.cloudbreak.domain.Network;
import com.sequenceiq.cloudbreak.domain.environment.AwsNetwork;
import com.sequenceiq.cloudbreak.domain.environment.AzureNetwork;
import com.sequenceiq.cloudbreak.domain.json.Json;
import com.sequenceiq.cloudbreak.domain.view.EnvironmentView;
import com.sequenceiq.cloudbreak.service.MissingResourceNameGenerator;

@Component
public class EnvironmentViewToNetworkConverter extends AbstractConversionServiceAwareConverter<EnvironmentView, Network> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentViewToNetworkConverter.class);

    @Inject
    private MissingResourceNameGenerator missingResourceNameGenerator;

    @Override
    public Network convert(EnvironmentView source) {
        Network result = new Network();
        result.setName(missingResourceNameGenerator.generateName(APIResourceType.NETWORK));
        result.setSubnetCIDR(null);

        String cloudPlatform = source.getCloudPlatform();
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("subnetId", String.join(",", source.getNetwork().getSubnetIdsSet()));
        if (AWS.equals(CloudPlatform.valueOf(source.getCloudPlatform()))) {
            AwsNetwork awsNetwork = (AwsNetwork) source.getNetwork();
            attributes.put("vpcId", awsNetwork.getVpcId());
        } else if (AZURE.equals(CloudPlatform.valueOf(source.getCloudPlatform()))) {
            AzureNetwork azureNetwork = (AzureNetwork) source.getNetwork();
            attributes.put("networkId", azureNetwork.getNetworkId());
            attributes.put("resourceGroupName", azureNetwork.getResourceGroupName());
            attributes.put("noPublicIp", azureNetwork.getNoPublicIp());
            attributes.put("noFirewallRules", azureNetwork.getNoFirewallRules());
        }
        attributes.put("cloudPlatform", cloudPlatform);
        try {
            result.setAttributes(new Json(attributes));
        } catch (JsonProcessingException e) {
            LOGGER.debug("Environment's network could not be converted to network.", e);
        }
        return result;
    }
}
