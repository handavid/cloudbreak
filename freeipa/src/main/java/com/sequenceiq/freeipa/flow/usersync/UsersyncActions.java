package com.sequenceiq.freeipa.flow.usersync;

import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;

import com.sequenceiq.cloudbreak.common.event.Selectable;
import com.sequenceiq.freeipa.flow.stack.StackEvent;
import com.sequenceiq.freeipa.flow.usersync.action.AbstractUsersyncAction;
import com.sequenceiq.freeipa.flow.usersync.event.SyncUsersRequest;
import com.sequenceiq.freeipa.service.stack.StackUpdater;

@Configuration
public class UsersyncActions {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersyncActions.class);

    // TODO store sync state either in stack or separately from stack
    @Inject
    private StackUpdater stackUpdater;

    @Bean(name = "SYNCING_USERS_STATE")
    public Action<?, ?> syncUsersAction() {
        return new AbstractUsersyncAction<>(StackEvent.class) {

            @Override
            protected void doExecute(UsersyncContext context, StackEvent payload, Map<Object, Object> variables) {
                LOGGER.info("UsersyncActions.doExecute() SYNCING_USERS_STATE");
                // TODO store usersync state
//                stackUpdater.updateStackStatus(context.getStack().getId(), DetailedStackStatus.BOOTSTRAPPING_MACHINES);
                sendEvent(context);
            }

            @Override
            protected Selectable createRequest(UsersyncContext context) {
                return new SyncUsersRequest(context.getResourceId());
            }
        };
    }

//    @Bean(name = "USERSYNC_FAILED_STATE")
//    public Action<?, ?> handleUsersyncFailure() {
//        return new AbstractStackFailureAction<UsersyncFlowState, StackEvent>() {
//
//            @Override
//            protected void doExecute(UsersyncContext context, StackFailureEvent payload, Map<Object, Object> variables) {
//                // TODO store usersync state
////                stackUpdater.updateStackStatus(context.getStack().getId(), DetailedStackStatus.PROVISION_FAILED);
//                sendEvent(context);
//            }
//
//            @Override
//            protected Selectable createRequest(StackFailureContext context) {
//                return new StackEvent(UsersyncEvent.USERSYNC_FAILURE_HANDLED_EVENT.event(), context.getStack().getId());
//            }
//        };
//    }
}
