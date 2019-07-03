package com.sequenceiq.freeipa.flow.freeipa.user;

import java.util.Optional;

import org.springframework.statemachine.StateContext;

import com.sequenceiq.flow.core.AbstractAction;
import com.sequenceiq.flow.core.Flow;
import com.sequenceiq.flow.core.FlowParameters;

public abstract class AbstractSyncFailureAction
        extends AbstractAction<SyncState, SyncFlowEvent, SyncFailureContext, SyncFailureEvent> {

    protected AbstractSyncFailureAction() {
        super(SyncFailureEvent.class);
    }

    @Override
    protected SyncFailureContext createFlowContext(FlowParameters flowParameters,
            StateContext<SyncState, SyncFlowEvent> stateContext, SyncFailureEvent payload) {
        Flow flow = getFlow(flowParameters.getFlowId());

        // TODO fill up the MDC with sync stuff
        // we should at least have the account id and user crn
//        MDCBuilder.buildMdcContext(stack);

        flow.setFlowFailed(payload.getException());
        return new SyncFailureContext(flowParameters, payload.getResourceId());
    }

    @Override
    protected Object getFailurePayload(SyncFailureEvent payload, Optional<SyncFailureContext> flowContext, Exception ex) {
        return null;
    }

    // TODO is there a comparable converter for sync failures?
//    @Override
//    protected void initPayloadConverterMap(List<PayloadConverter<SyncFailureEvent>> payloadConverters) {
//        payloadConverters.add(new CloudPlatformResponseToStackFailureConverter());
//    }
}
