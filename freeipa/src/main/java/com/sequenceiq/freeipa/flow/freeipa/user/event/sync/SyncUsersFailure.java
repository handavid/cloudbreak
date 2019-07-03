package com.sequenceiq.freeipa.flow.freeipa.user.event.sync;

import com.sequenceiq.freeipa.flow.freeipa.user.SyncFailureEvent;

public class SyncUsersFailure extends SyncFailureEvent {
    public SyncUsersFailure(Long syncId, Exception e) {
        super(syncId, e);
    }
}
