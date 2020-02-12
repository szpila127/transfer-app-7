package com.transfer.app7.mapper;

import com.transfer.app7.domain.Transaction;
import com.transfer.app7.domain.dto.TransactionDto;
import com.transfer.app7.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionMapper {

    @Autowired
    private AccountRepository accountRepository;

    public Transaction mapToTransaction(final TransactionDto transactionDto) {
        return new Transaction(
                transactionDto.getId(),
                transactionDto.getDate(),
                transactionDto.getAmount(),
                transactionDto.getCurrency(),
                accountRepository.findById(transactionDto.getAccountOutId()).orElseThrow(null),
                accountRepository.findById(transactionDto.getAccountInId()).orElseThrow(null));
    }

    public TransactionDto mapToTransactionDto(final Transaction transaction) {
        return new TransactionDto(
                transaction.getId(),
                transaction.getDate(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getAccountOut().getId(),
                transaction.getAccountIn().getId());
    }

    public List<TransactionDto> mapToTransactionDtoList(final List<Transaction> transactionList) {
        return transactionList.stream()
                .map(transaction -> new TransactionDto(
                        transaction.getId(),
                        transaction.getDate(),
                        transaction.getAmount(),
                        transaction.getCurrency(),
                        transaction.getAccountOut().getId(),
                        transaction.getAccountIn().getId()))
                .collect(Collectors.toList());
    }
}
