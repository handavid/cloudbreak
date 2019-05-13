package com.sequenceiq.freeipa.flow.usersync;

import static java.util.Objects.requireNonNull;

import com.sequenceiq.cloudbreak.core.flow2.CommonContext;

public class UsersyncContext extends CommonContext {

    private final Long resourceId;

    public UsersyncContext(String flowId, Long resourceId/*, TODO add users/groups structure */) {
        super(flowId);
        this.resourceId = requireNonNull(resourceId);
    }

    public Long getResourceId() {
        return resourceId;
    }
}