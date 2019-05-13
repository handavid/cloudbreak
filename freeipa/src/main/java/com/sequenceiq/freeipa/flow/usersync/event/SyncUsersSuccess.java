package com.sequenceiq.freeipa.flow.usersync.event;

import com.sequenceiq.cloudbreak.reactor.api.event.BaseFlowEvent;
import com.sequenceiq.freeipa.flow.event.EventSelectorUtil;

public class SyncUsersSuccess extends BaseFlowEvent {
    public SyncUsersSuccess(Long resourceId) {
        super(EventSelectorUtil.selector(SyncUsersSuccess.class), resourceId);
    }
}
