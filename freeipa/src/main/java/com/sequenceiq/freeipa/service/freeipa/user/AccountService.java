package com.sequenceiq.freeipa.service.freeipa.user;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.sequenceiq.freeipa.entity.Account;
import com.sequenceiq.freeipa.repository.AccountRepository;

@Service
public class AccountService {

    @Inject
    private AccountRepository accountRepository;

    public Account save(Account account) {
        return accountRepository.save(account);
    }

}
