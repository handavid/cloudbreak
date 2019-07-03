package com.sequenceiq.freeipa.flow.freeipa.user.action;

import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;

import com.sequenceiq.cloudbreak.common.event.Selectable;
import com.sequenceiq.freeipa.flow.freeipa.user.AbstractSyncAction;
import com.sequenceiq.freeipa.flow.freeipa.user.AbstractSyncFailureAction;
import com.sequenceiq.freeipa.flow.freeipa.user.SyncContext;
import com.sequenceiq.freeipa.flow.freeipa.user.SyncEvent;
import com.sequenceiq.freeipa.flow.freeipa.user.SyncFailureContext;
import com.sequenceiq.freeipa.flow.freeipa.user.SyncFailureEvent;
import com.sequenceiq.freeipa.flow.freeipa.user.SyncFlowEvent;
import com.sequenceiq.freeipa.flow.freeipa.user.event.sync.SyncUsersRequest;
import com.sequenceiq.freeipa.service.stack.StackUpdater;

@Configuration
public class UserSyncActions {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserSyncActions.class);

    // TODO store sync state either in stack or separately from stack
    // create SyncUpdater instead
    @Inject
    private StackUpdater stackUpdater;

    @Bean(name = "SYNCING_USERS_STATE")
    public Action<?, ?> syncUsersAction() {
        return new AbstractSyncAction<>(SyncEvent.class) {

            @Override
            protected void doExecute(SyncContext context, SyncEvent payload, Map<Object, Object> variables) {
                LOGGER.info("UsersyncActions.doExecute() SYNCING_USERS_STATE");
                // TODO store usersync state
//                stackUpdater.updateStackStatus(context.getStack().getId(), DetailedStackStatus.BOOTSTRAPPING_MACHINES);
                sendEvent(context);
            }

            @Override
            protected Selectable createRequest(SyncContext context) {
                return new SyncUsersRequest(context.getResourceId());
            }
        };
    }

    @Bean(name = "USERSYNC_FAILED_STATE")
    public Action<?, ?> handleUsersyncFailure() {
        return new AbstractSyncFailureAction() {
            @Override
            protected void doExecute(SyncFailureContext context, SyncFailureEvent payload, Map<Object, Object> variables) throws Exception {
                // TODO store usersync state
//                stackUpdater.updateStackStatus(context.getStack().getId(), DetailedStackStatus.PROVISION_FAILED);
                sendEvent(context);
            }

            @Override
            protected Selectable createRequest(SyncFailureContext context) {
                return new SyncEvent(SyncFlowEvent.SYNC_FAILURE_HANDLED_EVENT.event(), context.getResourceId());
            }
        };
    }
}
