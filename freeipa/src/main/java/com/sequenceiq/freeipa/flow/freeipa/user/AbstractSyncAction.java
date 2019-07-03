package com.sequenceiq.freeipa.flow.freeipa.user;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.statemachine.StateContext;

import com.sequenceiq.cloudbreak.common.event.Payload;
import com.sequenceiq.flow.core.AbstractAction;
import com.sequenceiq.flow.core.FlowParameters;

public abstract class AbstractSyncAction<P extends Payload> extends AbstractAction<SyncState, SyncFlowEvent, SyncContext, P> {
    protected AbstractSyncAction(Class<P> payloadClass) {
        super(payloadClass);
    }

    @PostConstruct
    public void init() {
        super.init();
    }

    @Override
    protected SyncContext createFlowContext(FlowParameters flowParameters, StateContext<SyncState, SyncFlowEvent> stateContext, P payload) {
        // TODO get account info from payload

        // TOD load MDC with relevant info
//        MDCBuilder.buildMdcContext(stack);

        return new SyncContext(flowParameters, payload.getResourceId());
    }

    @Override
    protected Object getFailurePayload(P payload, Optional<SyncContext> flowContext, Exception ex) {
        return new SyncFailureEvent(payload.getResourceId(), ex);
    }

}
