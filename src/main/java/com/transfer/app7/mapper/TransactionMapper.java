package com.transfer.app7.mapper;

import com.transfer.app7.domain.Transaction;
import com.transfer.app7.domain.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    @Autowired
    private AccountMapper accountMapper;

    public Transaction mapToTransaction(final TransactionDto transactionDto) {
        return new Transaction(
                transactionDto.getId(),
                transactionDto.getDate(),
                transactionDto.getAmount(),
                accountMapper.mapToAccount(transactionDto.getAccountOut()),
                accountMapper.mapToAccount(transactionDto.getAccountIn()));
    }

    public TransactionDto mapToTransactionDto(final Transaction transaction) {
        return new TransactionDto(
                transaction.getId(),
                transaction.getDate(),
                transaction.getAmount(),
                accountMapper.mapToAccountDto(transaction.getAccountOut()),
                accountMapper.mapToAccountDto(transaction.getAccountIn()));
    }
}
