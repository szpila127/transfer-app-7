package com.transfer.app7.facade;

import com.transfer.app7.mapper.TransactionMapper;
import com.transfer.app7.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionFacade {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionMapper transactionMapper;
}
