package com.sequenceiq.cloudbreak.controller.validation.environment;

import static com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable.CloudPlatform.AWS;
import static com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable.CloudPlatform.AZURE;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable.CloudPlatform;
import com.sequenceiq.cloudbreak.api.endpoint.v4.environment.base.EnvironmentNetworkAzureV4Params;
import com.sequenceiq.cloudbreak.api.endpoint.v4.environment.requests.EnvironmentNetworkV4Request;
import com.sequenceiq.cloudbreak.controller.validation.ValidationResult.ValidationResultBuilder;

@Component
public class EnvironmentNetworkValidator {

    public void validateNetwork(EnvironmentNetworkV4Request networkV4Request, String cloudPlatform, ValidationResultBuilder resultBuilder) {
        if (networkV4Request != null) {
            if (AWS.equals(CloudPlatform.valueOf(cloudPlatform))) {
                if (networkV4Request.getAws() != null) {
                    if (StringUtils.isEmpty(networkV4Request.getAws().getVpcId())) {
                        resultBuilder.error(missingParamErrorMessage("VPC identifier(vpcId)'", cloudPlatform));
                    }
                } else {
                    resultBuilder.error(missingParamsErrorMsg(AWS));
                }
            } else if (AZURE.equals(CloudPlatform.valueOf(cloudPlatform))) {
                EnvironmentNetworkAzureV4Params azureV4Params = networkV4Request.getAzure();
                if (azureV4Params != null) {
                    if (StringUtils.isEmpty(azureV4Params.getNetworkId())) {
                        resultBuilder.error(missingParamErrorMessage("network identifier(networkId)", cloudPlatform));
                    }
                    if (StringUtils.isEmpty(azureV4Params.getResourceGroupName())) {
                        resultBuilder.error(missingParamErrorMessage("resource group's name(resourceGroupName)", cloudPlatform));
                    }
                } else {
                    resultBuilder.error(missingParamsErrorMsg(AZURE));
                }
            }
        }
    }

    private String missingParamErrorMessage(String paramName, String cloudPlatform) {
        return String.format("The '%s' should be specified for the '%s' environment specific network!", paramName, cloudPlatform);
    }

    private String missingParamsErrorMsg(CloudPlatform cloudPlatform) {
        return String.format("The '%s' related network parameters should be specified!", cloudPlatform);
    }
}
