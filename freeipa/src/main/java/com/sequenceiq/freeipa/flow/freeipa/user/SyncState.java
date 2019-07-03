package com.sequenceiq.freeipa.flow.freeipa.user;

import com.sequenceiq.flow.core.FlowState;

public enum SyncState implements FlowState {
    INIT_STATE,
    SYNCING_USERS_STATE,
    SYNCING_USERS_FAILED_STATE,
    FINAL_STATE;
}
