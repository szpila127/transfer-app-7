package com.transfer.app7.facade;

import com.transfer.app7.exception.NotFoundException;
import com.transfer.app7.domain.dto.AccountDto;
import com.transfer.app7.mapper.AccountMapper;
import com.transfer.app7.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountFacade {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountMapper accountMapper;

    public void createAccount(AccountDto accountDto) {
        accountService.save(accountMapper.mapToAccount(accountDto));
    }

    public List<AccountDto> getAccounts() {
        return accountMapper.mapToAccountDtoList(accountService.getAllAccounts());
    }

    public Long countAccounts() {
        return accountService.countAccounts();
    }

    public AccountDto getAccount(Long accountId) throws NotFoundException {
        return accountMapper.mapToAccountDto(accountService.getAccount(accountId).orElseThrow(NotFoundException::new));
    }

    public void deleteAccount(Long accountId) {
        accountService.deleteAccount(accountId);
    }

    public AccountDto updateAccount(AccountDto accountDto) {
        return accountMapper.mapToAccountDto(accountService.save(accountMapper.mapToAccount(accountDto)));
    }
}
