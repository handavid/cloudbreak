package com.sequenceiq.freeipa.flow.usersync;

import com.sequenceiq.cloudbreak.core.flow2.FlowEvent;
import com.sequenceiq.freeipa.flow.event.EventSelectorUtil;
import com.sequenceiq.freeipa.flow.usersync.event.SyncUsersFailure;
import com.sequenceiq.freeipa.flow.usersync.event.SyncUsersSuccess;

public enum UsersyncFlowEvent implements FlowEvent {

    USERSYNC_START_EVENT("USERSYNC_START_EVENT"),
    USERSYNC_FAILED_EVENT(EventSelectorUtil.selector(SyncUsersFailure.class)),
    USERSYNC_FINISHED_EVENT(EventSelectorUtil.selector(SyncUsersSuccess.class)),
    USERSYNC_FAILURE_HANDLED_EVENT("USERSYNC_FAILURE_HANDLED_EVENT");

    private final String event;

    UsersyncFlowEvent(String event) {
        this.event = event;
    }

    @Override
    public String event() {
        return event;
    }
}
