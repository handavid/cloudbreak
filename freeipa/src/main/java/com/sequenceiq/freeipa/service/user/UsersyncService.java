// (c) Copyright 2019 Cloudera, Inc. All rights reserved.

package com.sequenceiq.freeipa.service.user;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sequenceiq.cloudbreak.auth.altus.GrpcUmsClient;

@Service
public class UsersyncService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersyncService.class);

    @Inject
    private GrpcUmsClient grpcUmsClient;

    public void synchronizeUsers() {
        String actorCrn = "actor";
        String userCrn = "user";
//        User user = grpcUmsClient.getUserDetails(actorCrn, userCrn, Optional.empty());

//        LOGGER.info("User for actor {} and user {} is {}.", actorCrn, userCrn, user);

    }
}
