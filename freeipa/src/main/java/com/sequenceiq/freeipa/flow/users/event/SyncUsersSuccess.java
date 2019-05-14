package com.sequenceiq.freeipa.flow.users.event;

public class SyncUsersSuccess extends BaseSyncEvent {
    public SyncUsersSuccess(Long stackId, Long syncId) {
        super(stackId, syncId);
    }
}