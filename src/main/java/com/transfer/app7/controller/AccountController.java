package com.transfer.app7.controller;

import com.transfer.app7.domain.dto.AccountDto;
import com.transfer.app7.facade.AccountFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/ta7/account")
public class AccountController {

    @Autowired
    private AccountFacade accountFacade;

    @PostMapping(consumes = "application/json")
    public String createAccount(@RequestBody AccountDto accountDto) {
        return accountFacade.createAccount(accountDto);
    }

    @GetMapping
    public List<AccountDto> getAccounts() {
        return accountFacade.getAccounts();
    }

    @GetMapping(value = "/count")
    public Long countAccounts() {
        return accountFacade.countAccounts();
    }

    @GetMapping(value = "/{id}")
    public AccountDto getAccount(@PathVariable("id") Long accountId) {
        return accountFacade.getAccount(accountId);
    }

    @DeleteMapping(value = "/{id}")
    public String deleteAccount(@PathVariable("id") Long accountId) {
        return accountFacade.deleteAccount(accountId);
    }

    @PutMapping(consumes = "application/json")
    public AccountDto updateAccount(@RequestBody AccountDto accountDto) {
        return accountFacade.updateAccount(accountDto);
    }
}
