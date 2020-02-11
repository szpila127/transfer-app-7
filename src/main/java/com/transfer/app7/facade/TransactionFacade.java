package com.transfer.app7.facade;

import com.transfer.app7.client.NbpApiClient;
import com.transfer.app7.domain.Account;
import com.transfer.app7.domain.Currency;
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
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

    public void createTransaction(TransactionDto transactionDto) {
        LOGGER.info("Creating a transaction...");
        Optional<Account> accountOut = accountService.getAccount(transactionDto.getAccountOutId());
        Optional<Account> accountIn = accountService.getAccount(transactionDto.getAccountInId());
        LOGGER.info("Getting info about accounts currencies");
        Currency accountOutCurrency = accountOut.get().getCurrency();
        Currency accountInCurrency = accountIn.get().getCurrency();
        LOGGER.info("Getting actual currencies courses");
        double outCurrencyFactor = nbpApiClient.getCurrencyFactor(accountOutCurrency.toString());
        double inCurrencyFactor = nbpApiClient.getCurrencyFactor(accountInCurrency.toString());
        double currencyChanger = inCurrencyFactor / outCurrencyFactor;
        int result = accountOut.get().getBalance().compareTo(transactionDto.getAmount().multiply(new BigDecimal(currencyChanger)));
        if (result >= 0) {
            LOGGER.info("Transaction in progress...");
            accountOut.get().setBalance(accountOut.get().getBalance().subtract(transactionDto.getAmount().multiply(new BigDecimal(currencyChanger))));
            accountService.save(accountOut.get());
            accountIn.get().setBalance(accountIn.get().getBalance().add(transactionDto.getAmount()));
            accountService.save(accountIn.get());
            transactionService.save(transactionMapper.mapToTransaction(transactionDto));
            LOGGER.info("Transaction complete :)");
            AppEventDto appEventDto = new AppEventDto(
                    Event.CREATE,
                    "Transaction: " +
                            "amount: " + transactionDto.getAmount() +
                            ", accountOutId: " + transactionDto.getAccountOutId() +
                            ", accountInId: " + transactionDto.getAccountInId());
            appEventFacade.createEvent(appEventDto);
        } else
            LOGGER.error("Not enough money!");
    }

    public List<TransactionDto> getTransactions() {
        LOGGER.info("Getting list of transactions");
        return transactionMapper.mapToTransactionDtoList(transactionService.getAllTransactions());
    }

    public Long countTransactions() {
        LOGGER.info("Counting transactions");
        return transactionService.countTransactions();
    }

    public TransactionDto getTransaction(Long transactionId) throws NotFoundException {
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
