package com.sequenceiq.freeipa.flow.users.event;

import static java.util.Objects.requireNonNull;

import com.sequenceiq.freeipa.flow.stack.StackEvent;

public class BaseSyncEvent extends StackEvent implements SyncEvent {

    private final Long syncId;

    public BaseSyncEvent(Long stackId, Long syncId) {
        super(stackId);
        this.syncId = requireNonNull(syncId);
    }

    public BaseSyncEvent(String selector, Long stackId, Long syncId) {
        super(selector, stackId);
        this.syncId = requireNonNull(syncId);
    }

    @Override
    public Long getSyncId() {
        return syncId;
    }
}
