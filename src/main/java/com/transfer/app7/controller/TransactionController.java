package com.transfer.app7.controller;

import com.transfer.app7.domain.TransactionDto;
import com.transfer.app7.facade.TransactionFacade;
import com.transfer.app7.mapper.TransactionMapper;
import com.transfer.app7.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/ta7/transaction")
public class TransactionController {

    @Autowired
    private TransactionFacade transactionFacade;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionMapper transactionMapper;

    @PostMapping(consumes = "application/json")
    public void createTransaction(@RequestBody TransactionDto transactionDto) {
        transactionService.save(transactionMapper.mapToTransaction(transactionDto));
    }

    @GetMapping
    public List<TransactionDto> getTransactions() {
        return transactionMapper.mapToTransactionDtoList(transactionService.getAllTransactions());
    }

    @GetMapping(value = "/count")
    public Long countTransactions() {
        return transactionService.countTransactions();
    }

    @GetMapping(value = "/{id}")
    public TransactionDto getTransaction(@PathVariable("id") Long transactionId) throws NotFoundException {
        return transactionMapper.mapToTransactionDto(transactionService.getTransaction(transactionId).orElseThrow(NotFoundException::new));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteTransaction(@PathVariable("id") Long transactionId) {
        transactionService.deleteTransaction(transactionId);
    }

    @PutMapping(consumes = "application/json")
    public TransactionDto updateTransaction(@RequestBody TransactionDto transactionDto) {
        return transactionMapper.mapToTransactionDto(transactionService.save(transactionMapper.mapToTransaction(transactionDto)));
    }


}
