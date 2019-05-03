package com.sequenceiq.freeipa.api.model.users;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
public class SynchronizeUsersResponse {

    private final String value;

    public SynchronizeUsersResponse(String value) {
        this.value = requireNonNull(value);
    }

    public String getValue() {
        return value;
    }
}
