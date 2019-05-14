package com.sequenceiq.freeipa.flow.users.event;

public class SyncUsersRequest extends BaseSyncEvent {
    public SyncUsersRequest(Long stackId, Long syncId) {
        super(stackId, syncId);
    }
}