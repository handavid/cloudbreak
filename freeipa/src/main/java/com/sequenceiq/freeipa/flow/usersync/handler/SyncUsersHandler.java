package com.sequenceiq.freeipa.flow.usersync.handler;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.common.event.Selectable;
import com.sequenceiq.freeipa.flow.event.EventSelectorUtil;
import com.sequenceiq.freeipa.flow.handler.EventHandler;
import com.sequenceiq.freeipa.flow.usersync.event.SyncUsersFailure;
import com.sequenceiq.freeipa.flow.usersync.event.SyncUsersRequest;
import com.sequenceiq.freeipa.flow.usersync.event.SyncUsersSuccess;
import com.sequenceiq.freeipa.service.user.UsersyncService;

import reactor.bus.Event;
import reactor.bus.EventBus;

@Component
public class SyncUsersHandler implements EventHandler<SyncUsersRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SyncUsersHandler.class);

    @Inject
    private EventBus eventBus;

    @Inject
    private UsersyncService usersyncService;

    @Override
    public String selector() {
        return EventSelectorUtil.selector(SyncUsersRequest.class);
    }

    @Override
    public void accept(Event<SyncUsersRequest> event) {
        LOGGER.info("accept() {}", event);
        SyncUsersRequest request = event.getData();
        Selectable response;
        try {
            usersyncService.synchronizeUsers(request.getStackId());
            response = new SyncUsersSuccess(request.getStackId());
        } catch (Exception e) {
            LOGGER.error("User sync failed", e);
            response = new SyncUsersFailure(request.getStackId(), e);
        }
        eventBus.notify(response.selector(), new Event<>(event.getHeaders(), response));
    }
}
