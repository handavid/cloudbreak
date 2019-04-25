package com.sequenceiq.freeipa.api.model.credential;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CredentialRequest extends CredentialBase {

}
