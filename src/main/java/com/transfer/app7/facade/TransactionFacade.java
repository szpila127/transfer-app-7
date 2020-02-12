package com.transfer.app7.facade;

import com.transfer.app7.client.NbpApiClient;
import com.transfer.app7.domain.Account;
import com.transfer.app7.domain.Event;
import com.transfer.app7.domain.dto.AppEventDto;
import com.transfer.app7.domain.dto.TransactionDto;
import com.transfer.app7.exception.NotFoundException;
import com.transfer.app7.mapper.TransactionMapper;
import com.transfer.app7.service.AccountService;
import com.transfer.app7.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@EnableAspectJAutoProxy
@Component
public class TransactionFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionFacade.class);

    @Autowired
    private AppEventFacade appEventFacade;

    @Autowired
    private NbpApiClient nbpApiClient;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionMapper transactionMapper;

    public String createTransaction(TransactionDto transactionDto) {
        LOGGER.info("Creating a transaction...");
        Account accountOut = accountService.getAccount(transactionDto.getAccountOutId()).orElseThrow(NotFoundException::new);
        Account accountIn = accountService.getAccount(transactionDto.getAccountInId()).orElseThrow(NotFoundException::new);
        LOGGER.info("Getting info about accounts currencies, actual currencies courses");
        double outCurrencyFactor = nbpApiClient.getCurrencyFactor(accountOut.getCurrency().toString());
        double inCurrencyFactor = nbpApiClient.getCurrencyFactor(accountIn.getCurrency().toString());
        double transactionCurrencyFactor = nbpApiClient.getCurrencyFactor(transactionDto.getCurrency().toString());
        double currencyChangerOut = transactionCurrencyFactor / outCurrencyFactor;
        double currencyChangerIn = transactionCurrencyFactor / inCurrencyFactor;
        boolean enoughMoney = accountOut.getBalance().compareTo(transactionDto.getAmount().multiply(new BigDecimal(currencyChangerOut))) > 0;
        if (enoughMoney) {
            handleTransaction(transactionDto, accountOut, accountIn, currencyChangerOut, currencyChangerIn);
            LOGGER.info("Transaction complete.");
            return "Transaction complete.";
        } else {
            LOGGER.error("Not enough money!");
            return "Not enough money!";
        }
    }

    private void handleTransaction(TransactionDto transactionDto, Account accountOut, Account accountIn, double currencyChangerOut, double currencyChangerIn) {
        LOGGER.info("Transaction in progress...");
        accountOut.setBalance(accountOut.getBalance().subtract(transactionDto.getAmount().multiply(new BigDecimal(currencyChangerOut))));
        accountService.save(accountOut);
        accountIn.setBalance(accountIn.getBalance().add(transactionDto.getAmount().multiply(new BigDecimal(currencyChangerIn))));
        accountService.save(accountIn);
        transactionService.save(transactionMapper.mapToTransaction(transactionDto));
        AppEventDto appEventDto = new AppEventDto(
                Event.CREATE,
                "Transaction: " +
                        "amount: " + transactionDto.getAmount() + " " + transactionDto.getCurrency() +
                        ", accountOutId: " + transactionDto.getAccountOutId() +
                        ", accountInId: " + transactionDto.getAccountInId());
        appEventFacade.createEvent(appEventDto);
    }

    public String returnTransaction(Long transactionId) {
        TransactionDto transactionDtoGet = transactionMapper.mapToTransactionDto(transactionService.getTransaction(transactionId).orElseThrow(NotFoundException::new));
        Long newAccountOutId = transactionDtoGet.getAccountInId();
        Long newAccountInId = transactionDtoGet.getAccountOutId();
        transactionDtoGet.setAccountInId(newAccountInId);
        transactionDtoGet.setAccountOutId(newAccountOutId);
        transactionDtoGet.setId(null);
        return createTransaction(transactionDtoGet);
    }

    public List<TransactionDto> getTransactions() {
        LOGGER.info("Getting list of transactions");
        return transactionMapper.mapToTransactionDtoList(transactionService.getAllTransactions());
    }

    public Long countTransactions() {
        LOGGER.info("Counting transactions");
        return transactionService.countTransactions();
    }

    public TransactionDto getTransaction(Long transactionId) {
        LOGGER.info("Getting transaction by id: " + transactionId);
        return transactionMapper.mapToTransactionDto(transactionService.getTransaction(transactionId).orElseThrow(NotFoundException::new));
    }

    public void deleteTransaction(Long transactionId) {
        AppEventDto appEventDto = new AppEventDto(
                Event.DELETE,
                "Transaction id: " + transactionId);
        appEventFacade.createEvent(appEventDto);
        transactionService.deleteTransaction(transactionId);
        LOGGER.info("Transaction: " + transactionId + " deleted");
    }

    public TransactionDto updateTransaction(TransactionDto transactionDto) {
        AppEventDto appEventDto = new AppEventDto(
                Event.UPDATE,
                "Transaction id: " + transactionDto.getId());
        appEventFacade.createEvent(appEventDto);
        LOGGER.info("Updating transaction id: " + transactionDto.getId());
        return transactionMapper.mapToTransactionDto(transactionService.save(transactionMapper.mapToTransaction(transactionDto)));
    }
}
