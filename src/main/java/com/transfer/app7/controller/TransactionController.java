package com.transfer.app7.controller;

import com.transfer.app7.domain.TransactionDto;
import com.transfer.app7.exception.NotFoundException;
import com.transfer.app7.facade.TransactionFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/ta7/transaction")
public class TransactionController {

    @Autowired
    private TransactionFacade transactionFacade;

    @PostMapping(consumes = "application/json")
    public void createTransaction(@RequestBody TransactionDto transactionDto) {
        transactionFacade.createTransaction(transactionDto);
    }

    @GetMapping
    public List<TransactionDto> getTransactions() {
        return transactionFacade.getTransactions();
    }

    @GetMapping(value = "/count")
    public Long countTransactions() {
        return transactionFacade.countTransactions();
    }

    @GetMapping(value = "/{id}")
    public TransactionDto getTransaction(@PathVariable("id") Long transactionId) throws NotFoundException {
        return transactionFacade.getTransaction(transactionId);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteTransaction(@PathVariable("id") Long transactionId) {
        transactionFacade.deleteTransaction(transactionId);
    }

    @PutMapping(consumes = "application/json")
    public TransactionDto updateTransaction(@RequestBody TransactionDto transactionDto) {
        return transactionFacade.updateTransaction(transactionDto);
    }
}
