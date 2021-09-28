package com.transfer.app7.service;

import com.transfer.app7.config.EmailConfig;
import com.transfer.app7.domain.Account;
import com.transfer.app7.domain.Currency;
import com.transfer.app7.domain.User;
import com.transfer.app7.repository.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTestSuite {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private EmailConfig emailConfig;

    @Test
    public void testGetAllAccount() {
        //Given
        User user = new User(1L, "sebek", "sebek", "919191919", new ArrayList<>());
        Account account1 = new Account(1L, new BigDecimal(1500), Currency.EUR, user);
        Account account2 = new Account(2L, new BigDecimal(13000), Currency.PLN, user);
        List<Account> list = new ArrayList<>();
        list.add(account1);
        list.add(account2);

        when(accountRepository.findAll()).thenReturn(list);
        //When
        List<Account> listGet = accountService.getAllAccounts();
        //Then
        assertEquals(2, listGet.size());
        assertEquals(new BigDecimal(13000), listGet.get(1).getBalance());
    }

    @Test
    public void testSave() {
        //Given
        User user = new User(1L, "sebek", "sebek", "919191919", new ArrayList<>());
        Account account1 = new Account(1L, new BigDecimal(1500), Currency.EUR, user);

        when(accountRepository.save(any(Account.class))).thenReturn(account1);
        when(emailConfig.getAdminMail()).thenReturn("sebek");
        doNothing().when(emailService).send(any());
        //When
        Account accountSave = accountService.save(account1, true);
        //Then
        assertEquals(Currency.EUR, accountSave.getCurrency());
    }

    @Test
    public void testGetAccount() {
        //Given
        User user = new User(1L, "sebek", "sebek", "919191919", new ArrayList<>());
        Account account1 = new Account(1L, new BigDecimal(1500), Currency.EUR, user);

        when(accountRepository.findById(1L)).thenReturn(java.util.Optional.of(account1));
        //When
        Optional<Account> accountGet = accountService.getAccount(1L);
        //Then
        assertEquals(new BigDecimal(1500), accountGet.get().getBalance());
    }

    @Test
    public void testDeleteAccount() {
        //Given
        doNothing().when(accountRepository).deleteById(1L);
        //When
        accountService.deleteAccount(1L);
        //Then
        verify(accountRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testCountAccounts() {
        //Given
        when(accountRepository.count()).thenReturn(10L);
        //When
        long amount = accountService.countAccounts();
        //Then
        assertEquals(10L, amount);
    }
}

