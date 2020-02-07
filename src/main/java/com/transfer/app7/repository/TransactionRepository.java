package com.transfer.app7.repository;

import com.transfer.app7.domain.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    @Override
    List<Transaction> findAll();

    @Override
    Transaction save(Transaction transaction);

    @Override
    Optional<Transaction> findById(Long id);

    @Override
    void deleteById(Long id);

    @Override
    long count();
}
