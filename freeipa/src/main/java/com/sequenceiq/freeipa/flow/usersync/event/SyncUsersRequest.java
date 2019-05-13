package com.sequenceiq.freeipa.flow.usersync.event;

import com.sequenceiq.cloudbreak.reactor.api.event.BaseFlowEvent;
import com.sequenceiq.freeipa.flow.event.EventSelectorUtil;

public class SyncUsersRequest extends BaseFlowEvent {
    public SyncUsersRequest(Long resourceId) {
        super(EventSelectorUtil.selector(SyncUsersRequest.class), resourceId);
    }
}
