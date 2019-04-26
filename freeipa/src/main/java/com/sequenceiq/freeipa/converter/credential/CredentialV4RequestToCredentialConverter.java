package com.sequenceiq.freeipa.converter.credential;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.sequenceiq.freeipa.api.model.credential.CredentialRequest;
import com.sequenceiq.freeipa.entity.Credential;

@Component
public class CredentialV4RequestToCredentialConverter implements Converter<CredentialRequest, Credential> {

    @Override
    public Credential convert(CredentialRequest source) {
        Credential credential = new Credential();
        credential.setAttributes(source.getSecret());
        credential.setName(source.getName());
        return credential;
    }

}
