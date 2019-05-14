package com.sequenceiq.freeipa.flow.users.event;

import static java.util.Objects.requireNonNull;

import com.sequenceiq.freeipa.flow.stack.StackFailureEvent;

public class BaseSyncFailureEvent extends StackFailureEvent implements SyncEvent {

    private final Long syncId;

    public BaseSyncFailureEvent(Long stackId, Long syncId, Exception exception) {
        super(stackId, exception);
        this.syncId = requireNonNull(syncId);
    }

    @Override
    public Long getSyncId() {
        return syncId;
    }
}
