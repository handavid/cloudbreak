package com.sequenceiq.freeipa.flow.usersync.event;

import com.sequenceiq.cloudbreak.reactor.api.event.BaseFlowEvent;
import com.sequenceiq.freeipa.flow.event.EventSelectorUtil;

public class SyncUsersFailure extends BaseFlowEvent {

    private final Exception exception;

    public SyncUsersFailure(Long resourceId, Exception exception) {
        super(EventSelectorUtil.failureSelector(SyncUsersFailure.class), resourceId);
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }
}
