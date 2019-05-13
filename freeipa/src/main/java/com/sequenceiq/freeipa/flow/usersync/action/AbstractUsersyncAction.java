package com.sequenceiq.freeipa.flow.usersync.action;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

import javax.inject.Inject;

import org.springframework.statemachine.StateContext;

import com.sequenceiq.cloudbreak.common.event.Payload;
import com.sequenceiq.cloudbreak.core.flow2.AbstractAction;
import com.sequenceiq.freeipa.flow.usersync.UsersyncContext;
import com.sequenceiq.freeipa.flow.usersync.UsersyncFlowEvent;
import com.sequenceiq.freeipa.flow.usersync.UsersyncFlowState;
import com.sequenceiq.freeipa.flow.usersync.event.SyncUsersFailure;
import com.sequenceiq.freeipa.service.stack.StackService;

public abstract class AbstractUsersyncAction<P extends Payload> extends AbstractAction<UsersyncFlowState, UsersyncFlowEvent, UsersyncContext, P> {

    @Inject
    private StackService stackService;

    private static Random RANDOM = new SecureRandom();

    protected AbstractUsersyncAction(Class<P> payloadClass) {
        super(payloadClass);
    }

    @Override
    protected UsersyncContext createFlowContext(String flowId, StateContext<UsersyncFlowState, UsersyncFlowEvent> stateContext, P payload) {
        // TODO HACK until we get an actual stack
//        Stack stack = stackService.getByIdWithListsInTransaction(payload.getStackId());
//        MDCBuilder.buildMdcContext(stack);
        return new UsersyncContext(flowId, RANDOM.nextLong());
    }

    @Override
    protected Object getFailurePayload(P payload, Optional<UsersyncContext> flowContext, Exception ex) {
        return new SyncUsersFailure(payload.getStackId(), ex);
    }
}
