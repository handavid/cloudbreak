package com.sequenceiq.freeipa.api.model.users;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sequenceiq.cloudbreak.api.endpoint.v4.JsonEntity;
import com.sequenceiq.freeipa.doc.UsersyncModelDescriptions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
public class SynchronizeUsersRequest implements JsonEntity {

    @NotNull
    @ApiModelProperty(value = UsersyncModelDescriptions.ENVIRONMENT_NAME, required = true)
    private String environmentName;

    @ApiModelProperty(value = UsersyncModelDescriptions.USERSYNC_GROUPS)
    private Set<Group> groups = new HashSet<>();

    @ApiModelProperty(value = UsersyncModelDescriptions.USERSYNC_USERS)
    private Set<User> users = new HashSet<>();

    public String getEnvironmentName() {
        return environmentName;
    }

    // TODO: figure out whether we need a separate name since we are expecting one freeipa per environment
    public String getName() {
        return environmentName;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public Set<User> getUsers() {
        return users;
    }
}
