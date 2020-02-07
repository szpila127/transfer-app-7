package com.transfer.app7.repository;

import com.transfer.app7.domain.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {

    @Override
    List<Account> findAll();

    @Override
    Account save(Account account);

    @Override
    Optional<Account> findById(Long id);

    @Override
    void deleteById(Long id);

    @Override
    long count();
}
