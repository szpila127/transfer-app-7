package com.transfer.app7.controller;

import com.transfer.app7.domain.TransactionDto;
import com.transfer.app7.facade.TransactionFacade;
import com.transfer.app7.mapper.TransactionMapper;
import com.transfer.app7.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        return new ArrayList<>();
    }
}
