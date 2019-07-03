package com.sequenceiq.freeipa.flow.chain;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.common.event.Selectable;
import com.sequenceiq.flow.core.chain.FlowEventChainFactory;
import com.sequenceiq.freeipa.flow.freeipa.user.SyncFlowEvent;
import com.sequenceiq.freeipa.flow.freeipa.user.SyncEvent;

@Component
public class UserSyncFlowEventChainFactory implements FlowEventChainFactory<SyncEvent> {
    @Override
    public String initEvent() {
        return FlowChainTriggers.USER_SYNC_TRIGGER_EVENT;
    }

    @Override
    public Queue<Selectable> createFlowTriggerEventQueue(SyncEvent event) {

        Queue<Selectable> flowEventChain = new ConcurrentLinkedQueue<>();
        flowEventChain.add(new SyncEvent(SyncFlowEvent.SYNC_START_EVENT.event(), event.getResourceId(), event.accepted()));
        return flowEventChain;
    }
}
