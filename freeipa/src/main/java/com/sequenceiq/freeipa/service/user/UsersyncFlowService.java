package com.sequenceiq.freeipa.service.user;

import java.util.Optional;
import java.util.Random;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sequenceiq.freeipa.api.model.users.SynchronizeUsersRequest;
import com.sequenceiq.freeipa.entity.Stack;
import com.sequenceiq.freeipa.flow.chain.FlowChainTriggers;
import com.sequenceiq.freeipa.flow.stack.StackEvent;
import com.sequenceiq.freeipa.repository.StackRepository;
import com.sequenceiq.freeipa.service.FreeIpaFlowManager;

@Service
public class UsersyncFlowService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersyncFlowService.class);

    private static final Random RANDOM = new Random();

    @Inject
    private StackRepository stackRepository;

    @Inject
    private FreeIpaFlowManager flowManager;

    public Long synchronizeUsers(SynchronizeUsersRequest request) {
        String envirionmentName = request.getEnvironmentName();
        String name = request.getName();
        Optional<Stack> stack = stackRepository.findByNameAndEnvironment(
                name, envirionmentName);
        if (stack.isEmpty()) {
            // TODO HACK - let unknown stacks pass to test control flow
            LOGGER.info("Stack not found for environment {} and name {}", envirionmentName, name);
//            throw new IllegalArgumentException("Stack not found for name and environment");
        }

        // TODO save request to database

        // TODO save status to status repository

        // TODO add sync id to event
        Long id = RANDOM.nextLong();
        // TODO figure out how events are accepted to ensure that we don't have any conflicts between the
        // sync id and stack id
        flowManager.notify(FlowChainTriggers.USERSYNC_TRIGGER_EVENT,
                new StackEvent(FlowChainTriggers.USERSYNC_TRIGGER_EVENT, id));
//        new StackEvent(FlowChainTriggers.USERSYNC_TRIGGER_EVENT, stack.get().getId()));

        // TODO return sync id so it can be returned by API
        return id;
    }
}
