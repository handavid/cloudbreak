package com.sequenceiq.freeipa.api.model.users;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sequenceiq.freeipa.doc.UsersyncModelDescriptions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
public class Group {

    @NotNull
    @ApiModelProperty(value = UsersyncModelDescriptions.GROUP_NAME, required = true)
    private String name;

    public String getName() {
        return name;
    }
}
