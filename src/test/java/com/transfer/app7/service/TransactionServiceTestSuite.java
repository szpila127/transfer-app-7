package com.transfer.app7.service;

import com.transfer.app7.domain.Account;
import com.transfer.app7.domain.Currency;
import com.transfer.app7.domain.Transaction;
import com.transfer.app7.domain.User;
import com.transfer.app7.repository.TransactionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTestSuite {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Test
    public void testGetAllTransactions() {
        //Given
        User user = new User(1L, "sebek", "sebek", "919191919", new ArrayList<>());
        Account account1 = new Account(1L, new BigDecimal(1500), Currency.EUR, user);
        Account account2 = new Account(2L, new BigDecimal(13000), Currency.PLN, user);
        Transaction transaction = new Transaction(1L, LocalDateTime.now(), new BigDecimal(9000), Currency.EUR, account1, account2);
        List<Transaction> list = new ArrayList<>();
        list.add(transaction);

        when(transactionRepository.findAll()).thenReturn(list);
        //When
        List<Transaction> listGet = transactionService.getAllTransactions();
        //Then
        assertEquals(1, listGet.size());
        assertEquals(Currency.EUR, listGet.get(0).getCurrency());
    }

    @Test
    public void testSave() {
        //Given
        User user = new User(1L, "sebek", "sebek", "919191919", new ArrayList<>());
        Account account1 = new Account(1L, new BigDecimal(1500), Currency.EUR, user);
        Account account2 = new Account(2L, new BigDecimal(13000), Currency.PLN, user);
        Transaction transaction = new Transaction(1L, LocalDateTime.now(), new BigDecimal(9000), Currency.EUR, account1, account2);

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        //When
        Transaction transactionSave = transactionService.save(transaction);
        //Then
        assertEquals(new BigDecimal(9000), transactionSave.getAmount());
    }

    @Test
    public void testGetTransaction() {
        //Given
        User user = new User(1L, "sebek", "sebek", "919191919", new ArrayList<>());
        Account account1 = new Account(1L, new BigDecimal(1500), Currency.EUR, user);
        Account account2 = new Account(2L, new BigDecimal(13000), Currency.PLN, user);
        Transaction transaction = new Transaction(1L, LocalDateTime.now(), new BigDecimal(9000), Currency.EUR, account1, account2);

        when(transactionRepository.findById(1L)).thenReturn(java.util.Optional.of(transaction));
        //When
        Optional<Transaction> transactionGet = transactionService.getTransaction(1l);
        //Then
        assertEquals(Currency.EUR, transactionGet.get().getCurrency());
    }

    @Test
    public void testDeleteTransaction() {
        //Given
        doNothing().when(transactionRepository).deleteById(1L);
        //When
        transactionService.deleteTransaction(1L);
        //Then
        verify(transactionRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testCountTransactions() {
        //Given
        when(transactionRepository.count()).thenReturn(10L);
        //When
        long amount = transactionService.countTransactions();
        //Then
        assertEquals(10L, amount);
    }
}
