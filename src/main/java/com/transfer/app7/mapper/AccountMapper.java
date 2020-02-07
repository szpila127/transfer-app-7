package com.transfer.app7.mapper;

import com.transfer.app7.domain.Account;
import com.transfer.app7.domain.AccountDto;
import com.transfer.app7.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountMapper {

    @Autowired
    private UserRepository userRepository;

    public Account mapToAccount(final AccountDto accountDto) {
        return new Account(
                accountDto.getId(),
                accountDto.getBalance(),
                accountDto.getCurrency(),
                userRepository.findById(accountDto.getUserId()).orElseThrow(null));
    }

    public AccountDto mapToAccountDto(final Account account) {
        return new AccountDto(
                account.getId(),
                account.getBalance(),
                account.getCurrency(),
                account.getUser().getId());
    }

    public List<AccountDto> mapToAccountDtoList(final List<Account> accountList) {
        return accountList.stream()
                .map(account -> new AccountDto(
                        account.getId(),
                        account.getBalance(),
                        account.getCurrency(),
                        account.getUser().getId()))
                .collect(Collectors.toList());
    }

    public List<Account> mapToAccountList(final List<AccountDto> accountDtoList) {
        return accountDtoList.stream()
                .map(accountDto -> new Account(
                        accountDto.getId(),
                        accountDto.getBalance(),
                        accountDto.getCurrency(),
                        userRepository.findById(accountDto.getUserId()).orElseThrow(null)))
                .collect(Collectors.toList());
    }
}
