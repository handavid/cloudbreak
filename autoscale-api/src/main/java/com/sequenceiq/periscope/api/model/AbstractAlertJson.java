package com.sequenceiq.periscope.api.model;

import javax.validation.constraints.Pattern;

import com.sequenceiq.periscope.doc.ApiDescription.BaseAlertJsonProperties;

import io.swagger.annotations.ApiModelProperty;

public abstract class AbstractAlertJson implements Json {

    @Pattern(regexp = "(^[a-zA-Z][-a-zA-Z0-9]*$)",
            message = "The name can only contain alphanumeric characters and hyphens and has start with an alphanumeric character")
    @ApiModelProperty(BaseAlertJsonProperties.ALERTNAME)
    private String alertName;

    @ApiModelProperty(BaseAlertJsonProperties.DESCRIPTION)
    private String description;

    public String getAlertName() {
        return alertName;
    }

    public void setAlertName(String alertName) {
        this.alertName = alertName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
