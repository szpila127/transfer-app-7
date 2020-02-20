package com.transfer.app7.facade;

import com.transfer.app7.domain.Event;
import com.transfer.app7.domain.dto.AccountDto;
import com.transfer.app7.domain.dto.AppEventDto;
import com.transfer.app7.exception.NotFoundException;
import com.transfer.app7.mapper.AccountMapper;
import com.transfer.app7.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountFacade.class);

    @Autowired
    private AppEventFacade appEventFacade;

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountMapper accountMapper;

    public String createAccount(AccountDto accountDto) {
        LOGGER.info("Creating an account");
        accountService.save(accountMapper.mapToAccount(accountDto));
        AppEventDto appEventDto = new AppEventDto(
                Event.CREATE,
                "Account " + accountDto.getCurrency() + " for " + userFacade.getUser(accountDto.getUserId()).getEmail() +
                        ", balance: " + accountDto.getBalance() + " " + accountDto.getCurrency());
        appEventFacade.createEvent(appEventDto);
        return "Account created.";
    }

    public List<AccountDto> getAccounts() {
        LOGGER.info("Getting list of accounts");
        return accountMapper.mapToAccountDtoList(accountService.getAllAccounts());
    }

    public Long countAccounts() {
        LOGGER.info("Counting accounts");
        return accountService.countAccounts();
    }

    public AccountDto getAccount(Long accountId) {
        LOGGER.info("Getting account by id: " + accountId);
        return accountMapper.mapToAccountDto(accountService.getAccount(accountId).orElseThrow(NotFoundException::new));
    }

    public void deleteAccount(Long accountId) {
        AppEventDto appEventDto = new AppEventDto(
                Event.DELETE,
                "Account id: " + accountId + ", User email: " + userFacade.getUser(accountService.getAccount(accountId).orElseThrow(NotFoundException::new).getUser().getId()).getEmail());
        appEventFacade.createEvent(appEventDto);
        accountService.deleteAccount(accountId);
        LOGGER.info("Account: " + accountId + " deleted");
    }

    public AccountDto updateAccount(AccountDto accountDto) {
        AppEventDto appEventDto = new AppEventDto(
                Event.UPDATE,
                "Account id: " + accountDto.getId() +
                        ", balance: " + accountService.getAccount(accountDto.getId()).orElseThrow(NotFoundException::new).getBalance() + " -> " + accountDto.getBalance() +
                        ", currency: " + accountService.getAccount(accountDto.getId()).orElseThrow(NotFoundException::new).getCurrency() + " -> " + accountDto.getCurrency() +
                        ", userId: " + accountService.getAccount(accountDto.getId()).orElseThrow(NotFoundException::new).getUser().getId() + " -> " + accountDto.getUserId());
        appEventFacade.createEvent(appEventDto);
        LOGGER.info("Updating account id: " + accountDto.getId());
        return accountMapper.mapToAccountDto(accountService.save(accountMapper.mapToAccount(accountDto)));
    }
}
