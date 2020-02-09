package com.transfer.app7.facade;

import com.transfer.app7.controller.NotFoundException;
import com.transfer.app7.domain.TransactionDto;
import com.transfer.app7.mapper.TransactionMapper;
import com.transfer.app7.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionFacade {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionMapper transactionMapper;

    public void createTransaction(TransactionDto transactionDto) {
        transactionService.save(transactionMapper.mapToTransaction(transactionDto));
    }

    public List<TransactionDto> getTransactions() {
        return transactionMapper.mapToTransactionDtoList(transactionService.getAllTransactions());
    }

    public Long countTransactions() {
        return transactionService.countTransactions();
    }

    public TransactionDto getTransaction(Long transactionId) throws NotFoundException {
        return transactionMapper.mapToTransactionDto(transactionService.getTransaction(transactionId).orElseThrow(NotFoundException::new));
    }

    public void deleteTransaction(Long transactionId) {
        transactionService.deleteTransaction(transactionId);
    }

    public TransactionDto updateTransaction(TransactionDto transactionDto) {
        return transactionMapper.mapToTransactionDto(transactionService.save(transactionMapper.mapToTransaction(transactionDto)));
    }
}
