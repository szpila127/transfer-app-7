package com.transfer.app7.facade;

import com.transfer.app7.mapper.AccountMapper;
import com.transfer.app7.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountFacade {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountMapper accountMapper;
}
