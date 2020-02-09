package com.transfer.app7.controller;

import com.transfer.app7.domain.AccountDto;
import com.transfer.app7.mapper.AccountMapper;
import com.transfer.app7.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/ta7/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountMapper accountMapper;

    @PostMapping(consumes = "application/json")
    public void createAccount(@RequestBody AccountDto accountDto) {
        accountService.save(accountMapper.mapToAccount(accountDto));
    }

    @GetMapping
    public List<AccountDto> getAccounts() {
        return accountMapper.mapToAccountDtoList(accountService.getAllAccounts());
    }

    @GetMapping(value = "/count")
    public Long countAccounts() {
        return accountService.countAccounts();
    }

    @GetMapping(value = "/{id}")
    public AccountDto getAccount(@PathVariable("id") Long accountId) throws NotFoundException {
        return accountMapper.mapToAccountDto(accountService.getAccount(accountId).orElseThrow(NotFoundException::new));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteAccount(@PathVariable("id") Long accountId) {
        accountService.deleteAccount(accountId);
    }

    @PutMapping(consumes = "application/json")
    public AccountDto updateAccount(@RequestBody AccountDto accountDto) {
        return accountMapper.mapToAccountDto(accountService.save(accountMapper.mapToAccount(accountDto)));
    }
}
