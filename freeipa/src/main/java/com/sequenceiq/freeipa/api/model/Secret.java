package com.sequenceiq.freeipa.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Secret {
    @ApiModelProperty(ModelDescriptions.SecretResponseModelDescription.ENGINE_PATH)
    private String enginePath;

    @ApiModelProperty(ModelDescriptions.SecretResponseModelDescription.SECRET_PATH)
    private String secretPath;

    public Secret() {
    }

    public Secret(String enginePath, String secretPath) {
        this.enginePath = enginePath;
        this.secretPath = secretPath;
    }

    public String getEnginePath() {
        return enginePath;
    }

    public void setEnginePath(String enginePath) {
        this.enginePath = enginePath;
    }

    public String getSecretPath() {
        return secretPath;
    }

    public void setSecretPath(String secretPath) {
        this.secretPath = secretPath;
    }
}
