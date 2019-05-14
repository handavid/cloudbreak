package com.sequenceiq.freeipa.flow.users.event;

public class SyncUsersFailure extends BaseSyncFailureEvent {

    public SyncUsersFailure(Long stackId, Long syncId, Exception exception) {
        super(stackId, syncId, exception);
    }
}