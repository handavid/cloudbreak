package com.sequenceiq.freeipa.flow.freeipa.user;


import static com.sequenceiq.freeipa.flow.freeipa.user.SyncFlowEvent.SYNC_FAILED_EVENT;
import static com.sequenceiq.freeipa.flow.freeipa.user.SyncFlowEvent.SYNC_FAILURE_HANDLED_EVENT;
import static com.sequenceiq.freeipa.flow.freeipa.user.SyncFlowEvent.SYNC_FINISHED_EVENT;
import static com.sequenceiq.freeipa.flow.freeipa.user.SyncFlowEvent.SYNC_START_EVENT;
import static com.sequenceiq.freeipa.flow.freeipa.user.SyncState.FINAL_STATE;
import static com.sequenceiq.freeipa.flow.freeipa.user.SyncState.INIT_STATE;
import static com.sequenceiq.freeipa.flow.freeipa.user.SyncState.SYNCING_USERS_FAILED_STATE;
import static com.sequenceiq.freeipa.flow.freeipa.user.SyncState.SYNCING_USERS_STATE;

import java.util.List;

import org.springframework.stereotype.Component;

import com.sequenceiq.flow.core.config.AbstractFlowConfiguration;
import com.sequenceiq.flow.core.config.AbstractFlowConfiguration.Transition.Builder;

@Component
public class SyncFlowConfig extends AbstractFlowConfiguration<SyncState, SyncFlowEvent> {

    private static final SyncFlowEvent[] USER_SYNC_INIT_EVENTS = {SyncFlowEvent.SYNC_START_EVENT};

    private static final FlowEdgeConfig<SyncState, SyncFlowEvent> EDGE_CONFIG =
            new FlowEdgeConfig<>(INIT_STATE, FINAL_STATE, SYNCING_USERS_FAILED_STATE, SYNC_FAILURE_HANDLED_EVENT);

    private static final List<Transition<SyncState, SyncFlowEvent>> TRANSITIONS =
            new Builder<SyncState, SyncFlowEvent>().defaultFailureEvent(SYNC_FAILED_EVENT)
            .from(INIT_STATE).to(SYNCING_USERS_STATE).event(SYNC_START_EVENT).noFailureEvent()
            .from(SYNCING_USERS_STATE).to(FINAL_STATE).event(SYNC_FINISHED_EVENT).defaultFailureEvent()
            .build();

    public SyncFlowConfig() {
        super(SyncState.class, SyncFlowEvent.class);
    }

    @Override
    protected List<Transition<SyncState, SyncFlowEvent>> getTransitions() {
        return TRANSITIONS;
    }

    @Override
    protected FlowEdgeConfig<SyncState, SyncFlowEvent> getEdgeConfig() {
        return EDGE_CONFIG;
    }

    @Override
    public SyncFlowEvent[] getEvents() {
        return SyncFlowEvent.values();
    }

    @Override
    public SyncFlowEvent[] getInitEvents() {
        return USER_SYNC_INIT_EVENTS;
    }
}
