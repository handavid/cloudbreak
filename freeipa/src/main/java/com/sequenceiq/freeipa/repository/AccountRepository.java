package com.sequenceiq.freeipa.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.sequenceiq.freeipa.entity.Account;

@Transactional(Transactional.TxType.REQUIRED)
public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> getByAccountId(String accountId);
}
