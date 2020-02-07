package com.transfer.app7.mapper;

import com.transfer.app7.domain.Account;
import com.transfer.app7.domain.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountMapper {

    @Autowired
    private UserMapper userMapper;

    public Account mapToAccount(final AccountDto accountDto) {
        return new Account(
                accountDto.getId(),
                accountDto.getBalance(),
                accountDto.getCurrency(),
                userMapper.mapToUser(accountDto.getUser()));
    }

    public AccountDto mapToAccountDto(final Account account) {
        return new AccountDto(
                account.getId(),
                account.getBalance(),
                account.getCurrency(),
                userMapper.maToUserDto(account.getUser()));
    }

    public List<AccountDto> mapToAccountDtoList(final List<Account> accountList) {
        return accountList.stream()
                .map(account -> new AccountDto(
                        account.getId(),
                        account.getBalance(),
                        account.getCurrency(),
                        userMapper.maToUserDto(account.getUser())))
                .collect(Collectors.toList());
    }

    public List<Account> mapToAccountList(final List<AccountDto> accountDtoList) {
        return accountDtoList.stream()
                .map(accountDto -> new Account(
                        accountDto.getId(),
                        accountDto.getBalance(),
                        accountDto.getCurrency(),
                        userMapper.mapToUser(accountDto.getUser())))
                .collect(Collectors.toList());
    }
}
