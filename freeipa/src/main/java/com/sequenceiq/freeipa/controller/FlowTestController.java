package com.sequenceiq.freeipa.controller;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.stereotype.Controller;

import com.sequenceiq.freeipa.api.model.FlowTestEndpoint;
import com.sequenceiq.freeipa.service.FreeIpaFlowManager;

@Controller
@Transactional(TxType.NEVER)
public class FlowTestController implements FlowTestEndpoint {
    @Inject
    private FreeIpaFlowManager freeIpaFlowManager;

    @Override
    public void triggerTestFlow() {
        freeIpaFlowManager.triggerHelloworld();
    }
}
