package com.sequenceiq.freeipa.converter.instance;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable.ProviderParameterCalculator;
import com.sequenceiq.cloudbreak.converter.AbstractConversionServiceAwareConverter;
import com.sequenceiq.freeipa.api.model.instance.InstanceGroupV4Request;
import com.sequenceiq.freeipa.controller.exception.BadRequestException;
import com.sequenceiq.freeipa.entity.InstanceGroup;
import com.sequenceiq.freeipa.entity.InstanceMetaData;
import com.sequenceiq.freeipa.entity.SecurityGroup;
import com.sequenceiq.freeipa.entity.Template;
import com.sequenceiq.freeipa.entity.json.Json;

@Component
public class InstanceGroupV4RequestToInstanceGroupConverter extends AbstractConversionServiceAwareConverter<InstanceGroupV4Request, InstanceGroup> {

    @Inject
    private ProviderParameterCalculator providerParameterCalculator;

    @Override
    public InstanceGroup convert(InstanceGroupV4Request source) {
        InstanceGroup instanceGroup = new InstanceGroup();
        source.getTemplate().setCloudPlatform(source.getCloudPlatform());
        instanceGroup.setTemplate(getConversionService().convert(source.getTemplate(), Template.class));
        instanceGroup.setSecurityGroup(getConversionService().convert(source.getSecurityGroup(), SecurityGroup.class));
        instanceGroup.setGroupName(source.getName());
        setAttributes(source, instanceGroup);
        instanceGroup.setInstanceGroupType(source.getType());
        if (source.getNodeCount() > 0) {
            addInstanceMetadatas(source, instanceGroup);
        }
        return instanceGroup;
    }

    private void addInstanceMetadatas(InstanceGroupV4Request request, InstanceGroup instanceGroup) {
        Set<InstanceMetaData> instanceMetaDataSet = new HashSet<>();
        for (int i = 0; i < request.getNodeCount(); i++) {
            InstanceMetaData instanceMetaData = new InstanceMetaData();
            instanceMetaData.setInstanceGroup(instanceGroup);
            instanceMetaDataSet.add(instanceMetaData);
        }
        instanceGroup.setInstanceMetaData(instanceMetaDataSet);
    }

    private void setAttributes(InstanceGroupV4Request source, InstanceGroup instanceGroup) {
        Map<String, Object> parameters = providerParameterCalculator.get(source).asMap();
        if (parameters != null) {
            try {
                instanceGroup.setAttributes(new Json(parameters));
            } catch (JsonProcessingException ignored) {
                throw new BadRequestException("Invalid parameters");
            }
        }
    }
}
