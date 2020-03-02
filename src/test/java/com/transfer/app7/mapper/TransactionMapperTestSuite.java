package com.transfer.app7.mapper;

import com.transfer.app7.domain.Account;
import com.transfer.app7.domain.Currency;
import com.transfer.app7.domain.Transaction;
import com.transfer.app7.domain.User;
import com.transfer.app7.domain.dto.TransactionDto;
import com.transfer.app7.repository.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionMapperTestSuite {

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Test
    public void testMapToTransaction() {
        //Given
        TransactionDto transactionDto = new TransactionDto(
                9L, LocalDateTime.now(), new BigDecimal(1000), Currency.EUR, 10L, 11L);
        User user = new User(1L, "sebek", "sebek", "919191919", new ArrayList<>());
        Account account10 = new Account(10L, new BigDecimal(10000), Currency.EUR, user);
        Account account11 = new Account(11L, new BigDecimal(20000), Currency.EUR, user);

        when(accountRepository.findById(transactionDto.getAccountInId())).thenReturn(java.util.Optional.of(account11));
        when(accountRepository.findById(transactionDto.getAccountOutId())).thenReturn(java.util.Optional.of(account10));
        //When
        Transaction transaction = transactionMapper.mapToTransaction(transactionDto);
        //Then
        assertEquals(Currency.EUR, transaction.getCurrency());
        assertEquals(new BigDecimal(1000), transaction.getAmount());
        assertEquals(new BigDecimal(10000), transaction.getAccountOut().getBalance());
    }

    @Test
    public void testMapToTransactionDto() {
        //Given
        User user = new User(1L, "sebek", "sebek", "919191919", new ArrayList<>());
        Account account10 = new Account(10L, new BigDecimal(10000), Currency.EUR, user);
        Account account11 = new Account(11L, new BigDecimal(20000), Currency.EUR, user);
        Transaction transaction = new Transaction(
                9L, LocalDateTime.now(), new BigDecimal(1000), Currency.EUR, account10, account11);
        //When
        TransactionDto transactionDto = transactionMapper.mapToTransactionDto(transaction);
        //Then
        assertEquals(Currency.EUR, transactionDto.getCurrency());
        assertEquals(new BigDecimal(1000), transactionDto.getAmount());
    }

    @Test
    public void testMapToTransactionDtoList() {
        //Given
        User user1 = new User(1L, "sebek", "sebek", "919191919", new ArrayList<>());
        Account account10 = new Account(10L, new BigDecimal(10000), Currency.EUR, user1);
        Account account11 = new Account(11L, new BigDecimal(20000), Currency.EUR, user1);
        Transaction transaction1 = new Transaction(
                4L, LocalDateTime.now(), new BigDecimal(1000), Currency.EUR, account10, account11);
        User user2 = new User(2L, "krzys", "krzys", "919191919", new ArrayList<>());
        Account account12 = new Account(12L, new BigDecimal(1000), Currency.GBP, user2);
        Account account13 = new Account(13L, new BigDecimal(2000), Currency.GBP, user2);
        Transaction transaction2 = new Transaction(
                3L, LocalDateTime.now(), new BigDecimal(100), Currency.GBP, account10, account11);
        List<Transaction> list = new ArrayList<>();
        list.add(transaction1);
        list.add(transaction2);
        //When
        List<TransactionDto> listDto = transactionMapper.mapToTransactionDtoList(list);
        //Then
        assertEquals(2, listDto.size());
        assertEquals(new BigDecimal(100), listDto.get(1).getAmount());
        assertEquals(Currency.EUR, listDto.get(0).getCurrency());
    }
}
