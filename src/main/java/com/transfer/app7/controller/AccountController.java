package com.transfer.app7.controller;

import com.transfer.app7.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/ta7/account")
public class AccountController {

    @Autowired
    private AccountService accountService;
}
