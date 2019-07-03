package com.sequenceiq.freeipa.flow.freeipa.user;

import com.sequenceiq.flow.core.CommonContext;
import com.sequenceiq.flow.core.FlowParameters;

public class SyncContext extends CommonContext {

    private final Long resourceId;

    public SyncContext(FlowParameters flowParameters, Long resourceId) {
        super(flowParameters);
        this.resourceId = resourceId;
    }

    public Long getResourceId() {
        return resourceId;
    }
}
