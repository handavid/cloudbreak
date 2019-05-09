package com.sequenceiq.freeipa.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.sequenceiq.freeipa.api.model.users.SynchronizeUsersRequest;
import com.sequenceiq.freeipa.api.model.users.SynchronizeUsersResponse;
import com.sequenceiq.freeipa.api.model.users.SynchronizeUsersStatus;
import com.sequenceiq.freeipa.api.model.users.UsersyncEndpoint;
import com.sequenceiq.freeipa.service.user.UsersyncService;

@Controller
public class UsersyncController implements UsersyncEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersyncController.class);

    @Inject
    private UsersyncService usersyncService;

    @Override
    public SynchronizeUsersResponse synchronizeUsers(SynchronizeUsersRequest request) {
        LOGGER.info("synchronizeUsers()");
        return new SynchronizeUsersResponse("Hello synchronizeUsers()!");

    }

    @Override
    public SynchronizeUsersStatus getStatus() {
        LOGGER.info("getStatus()");
        return new SynchronizeUsersStatus("Hello getStatus()!");
    }
}
