package com.sequenceiq.freeipa.flow.users.action;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.statemachine.StateContext;

import com.sequenceiq.cloudbreak.common.event.Payload;
import com.sequenceiq.cloudbreak.core.flow2.AbstractAction;
import com.sequenceiq.freeipa.flow.users.UsersyncContext;
import com.sequenceiq.freeipa.flow.users.UsersyncFlowEvent;
import com.sequenceiq.freeipa.flow.users.UsersyncFlowState;
import com.sequenceiq.freeipa.flow.users.event.SyncEvent;
import com.sequenceiq.freeipa.flow.users.event.SyncUsersFailure;
import com.sequenceiq.freeipa.service.stack.StackService;

public abstract class AbstractUsersyncAction<P extends Payload & SyncEvent> extends AbstractAction<UsersyncFlowState, UsersyncFlowEvent, UsersyncContext, P> {

    @Inject
    private StackService stackService;

    protected AbstractUsersyncAction(Class<P> payloadClass) {
        super(payloadClass);
    }

    @Override
    protected UsersyncContext createFlowContext(String flowId, StateContext<UsersyncFlowState, UsersyncFlowEvent> stateContext, P payload) {
        // TODO HACK until we get an actual stack
//        Stack stack = stackService.getByIdWithListsInTransaction(payload.getStackId());
//        MDCBuilder.buildMdcContext(stack);
        return new UsersyncContext(flowId, payload.getStackId(), payload.getSyncId());
    }

    @Override
    protected Object getFailurePayload(P payload, Optional<UsersyncContext> flowContext, Exception ex) {
        return new SyncUsersFailure(payload.getStackId(), payload.getSyncId(), ex);
    }
}
