package com.sequenceiq.freeipa.flow.users;

import static java.util.Objects.requireNonNull;

import com.sequenceiq.cloudbreak.core.flow2.CommonContext;

public class UsersyncContext extends CommonContext {

    private final Long stackId;

    private final Long syncId;

    public UsersyncContext(String flowId, Long stackId, Long syncId) {
        super(flowId);
        this.stackId = requireNonNull(stackId);
        this.syncId = requireNonNull(syncId);
    }

    public Long getStackId() {
        return stackId;
    }

    public Long getSyncId() {
        return syncId;
    }
}