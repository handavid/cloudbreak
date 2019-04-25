package com.sequenceiq.freeipa.api.model.freeipa;

public abstract class FreeIpaBase {

    private String domainName;

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }
}
