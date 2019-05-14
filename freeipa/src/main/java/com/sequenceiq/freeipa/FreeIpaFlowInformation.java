package com.sequenceiq.freeipa;

import static com.sequenceiq.freeipa.flow.users.UsersyncFlowEvent.USERSYNC_START_EVENT;

import java.util.List;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.core.flow2.ApplicationFlowInformation;
import com.sequenceiq.cloudbreak.core.flow2.config.FlowConfiguration;

@Component
public class FreeIpaFlowInformation implements ApplicationFlowInformation {
    @Override
    public List<Class<? extends FlowConfiguration<?>>> getRestartableFlows() {
        return List.of();
    }

    @Override
    public List<String> getAllowedParallelFlows() {
        return List.of(USERSYNC_START_EVENT.event());
    }
}
