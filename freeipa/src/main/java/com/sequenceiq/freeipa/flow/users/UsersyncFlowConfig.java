package com.sequenceiq.freeipa.flow.users;

import static com.sequenceiq.freeipa.flow.users.UsersyncFlowEvent.USERSYNC_FAILED_EVENT;
import static com.sequenceiq.freeipa.flow.users.UsersyncFlowEvent.USERSYNC_FAILURE_HANDLED_EVENT;
import static com.sequenceiq.freeipa.flow.users.UsersyncFlowEvent.USERSYNC_FINISHED_EVENT;
import static com.sequenceiq.freeipa.flow.users.UsersyncFlowEvent.USERSYNC_START_EVENT;
import static com.sequenceiq.freeipa.flow.users.UsersyncFlowState.FINAL_STATE;
import static com.sequenceiq.freeipa.flow.users.UsersyncFlowState.INIT_STATE;
import static com.sequenceiq.freeipa.flow.users.UsersyncFlowState.SYNCING_USERS_STATE;
import static com.sequenceiq.freeipa.flow.users.UsersyncFlowState.USERSYNC_FAILED_STATE;

import java.util.List;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.core.flow2.config.AbstractFlowConfiguration;
import com.sequenceiq.cloudbreak.core.flow2.config.AbstractFlowConfiguration.Transition.Builder;

@Component
public class UsersyncFlowConfig extends AbstractFlowConfiguration<UsersyncFlowState, UsersyncFlowEvent> {

    private static final UsersyncFlowEvent[] USERSYNC_INIT_EVENTS = {USERSYNC_START_EVENT};

    private static final FlowEdgeConfig<UsersyncFlowState, UsersyncFlowEvent> EDGE_CONFIG =
            new FlowEdgeConfig<>(INIT_STATE, FINAL_STATE, USERSYNC_FAILED_STATE, USERSYNC_FAILURE_HANDLED_EVENT);

    private static final List<Transition<UsersyncFlowState, UsersyncFlowEvent>> TRANSITIONS =
            new Builder<UsersyncFlowState, UsersyncFlowEvent>().defaultFailureEvent(USERSYNC_FAILED_EVENT)
                    .from(INIT_STATE).to(SYNCING_USERS_STATE).event(USERSYNC_START_EVENT).noFailureEvent()
                    .from(SYNCING_USERS_STATE).to(FINAL_STATE).event(USERSYNC_FINISHED_EVENT).defaultFailureEvent()
                    .build();

    public UsersyncFlowConfig() {
        super(UsersyncFlowState.class, UsersyncFlowEvent.class);
    }

    @Override
    protected List<Transition<UsersyncFlowState, UsersyncFlowEvent>> getTransitions() {
        return TRANSITIONS;
    }

    @Override
    protected FlowEdgeConfig<UsersyncFlowState, UsersyncFlowEvent> getEdgeConfig() {
        return EDGE_CONFIG;
    }

    @Override
    public UsersyncFlowEvent[] getEvents() {
        return UsersyncFlowEvent.values();
    }

    @Override
    public UsersyncFlowEvent[] getInitEvents() {
        return USERSYNC_INIT_EVENTS;
    }
}
