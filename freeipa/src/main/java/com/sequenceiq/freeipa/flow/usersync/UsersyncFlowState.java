package com.sequenceiq.freeipa.flow.usersync;

import com.sequenceiq.cloudbreak.core.flow2.FlowState;

public enum UsersyncFlowState implements FlowState {
    INIT_STATE,
    SYNCING_USERS_STATE,
    FINAL_STATE,
    USERSYNC_FAILED_STATE;
}
