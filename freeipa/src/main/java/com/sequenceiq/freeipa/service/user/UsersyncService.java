package com.sequenceiq.freeipa.service.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UsersyncService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersyncService.class);

    public void synchronizeUsers(Long stackId) {
        LOGGER.info("UsersyncService.synchronizeUsers() called");
    }
}
