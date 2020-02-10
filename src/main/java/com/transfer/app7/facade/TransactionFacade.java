package com.transfer.app7.facade;

import com.transfer.app7.client.NbpApiClient;
import com.transfer.app7.domain.Account;
import com.transfer.app7.domain.Currency;
import com.transfer.app7.domain.TransactionDto;
import com.transfer.app7.exception.NotFoundException;
import com.transfer.app7.mapper.TransactionMapper;
import com.transfer.app7.service.AccountService;
import com.transfer.app7.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class TransactionFacade {

    @Autowired
    private NbpApiClient nbpApiClient;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionMapper transactionMapper;

    public void createTransaction(TransactionDto transactionDto) {
        Optional<Account> accountOut = accountService.getAccount(transactionDto.getAccountOutId());
        Optional<Account> accountIn = accountService.getAccount(transactionDto.getAccountInId());
        Currency accountOutCurrency = accountOut.get().getCurrency();
        Currency accountInCurrency = accountIn.get().getCurrency();
        double outCurrencyFactor = nbpApiClient.getCurrencyFactor(accountOutCurrency.toString());
        double inCurrencyFactor = nbpApiClient.getCurrencyFactor(accountInCurrency.toString());
        double currencyChanger = inCurrencyFactor / outCurrencyFactor;
        int result = accountOut.get().getBalance().compareTo(transactionDto.getAmount().multiply(new BigDecimal(currencyChanger)));
        if (result >= 0) {
            accountOut.get().setBalance(accountOut.get().getBalance().subtract(transactionDto.getAmount().multiply(new BigDecimal(currencyChanger))));
            accountService.save(accountOut.get());
            accountIn.get().setBalance(accountIn.get().getBalance().add(transactionDto.getAmount()));
            accountService.save(accountIn.get());
            transactionService.save(transactionMapper.mapToTransaction(transactionDto));
        }
    }

    public List<TransactionDto> getTransactions() {
        return transactionMapper.mapToTransactionDtoList(transactionService.getAllTransactions());
    }

    public Long countTransactions() {
        return transactionService.countTransactions();
    }

    public TransactionDto getTransaction(Long transactionId) throws NotFoundException {
        return transactionMapper.mapToTransactionDto(transactionService.getTransaction(transactionId).orElseThrow(NotFoundException::new));
    }

    public void deleteTransaction(Long transactionId) {
        transactionService.deleteTransaction(transactionId);
    }

    public TransactionDto updateTransaction(TransactionDto transactionDto) {
        return transactionMapper.mapToTransactionDto(transactionService.save(transactionMapper.mapToTransaction(transactionDto)));
    }
}
