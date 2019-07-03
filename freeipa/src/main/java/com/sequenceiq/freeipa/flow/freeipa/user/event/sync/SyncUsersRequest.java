package com.sequenceiq.freeipa.flow.freeipa.user.event.sync;

import com.sequenceiq.freeipa.flow.freeipa.user.SyncEvent;

public class SyncUsersRequest extends SyncEvent {
    public SyncUsersRequest(Long syncId) {
        super(syncId);
    }
}
