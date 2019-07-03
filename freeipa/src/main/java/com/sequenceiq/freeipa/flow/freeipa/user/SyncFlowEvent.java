package com.sequenceiq.freeipa.flow.freeipa.user;

import com.sequenceiq.flow.core.FlowEvent;
import com.sequenceiq.flow.event.EventSelectorUtil;
import com.sequenceiq.freeipa.flow.freeipa.user.event.sync.SyncUsersFailure;
import com.sequenceiq.freeipa.flow.freeipa.user.event.sync.SyncUsersSuccess;

public enum SyncFlowEvent implements FlowEvent {
    SYNC_START_EVENT("SYNC_START_EVENT"),
    SYNC_FINISHED_EVENT(EventSelectorUtil.selector(SyncUsersSuccess.class)),
    SYNC_FAILED_EVENT(EventSelectorUtil.selector(SyncUsersFailure.class)),
    SYNC_FAILURE_HANDLED_EVENT("SYNC_FAILURE_HANDLED_EVENT");

    private final String event;

    SyncFlowEvent(String event) {
        this.event = event;
    }

    @Override
    public String event() {
        return event;
    }

}
