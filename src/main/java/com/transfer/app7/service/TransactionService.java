package com.transfer.app7.service;

import com.transfer.app7.domain.Transaction;
import com.transfer.app7.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Optional<Transaction> getTransaction(final Long id) {
        return transactionRepository.findById(id);
    }

    public void deleteTransaction(final Long id) {
        transactionRepository.deleteById(id);
    }

    public long countTransactions() {
        return transactionRepository.count();
    }
}
