package com.sequenceiq.freeipa.flow.freeipa.user.event.sync;

import com.sequenceiq.freeipa.flow.freeipa.user.SyncEvent;

public class SyncUsersSuccess extends SyncEvent {
    public SyncUsersSuccess(Long syncId) {
        super(syncId);
    }
}
