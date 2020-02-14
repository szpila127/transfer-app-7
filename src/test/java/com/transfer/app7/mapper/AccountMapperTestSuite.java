package com.transfer.app7.mapper;

import com.transfer.app7.domain.Account;
import com.transfer.app7.domain.Currency;
import com.transfer.app7.domain.User;
import com.transfer.app7.domain.dto.AccountDto;
import com.transfer.app7.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AccountMapperTestSuite {

    @Autowired
    private AccountMapper accountMapper;

    @MockBean
    private UserRepository userRepository;


    @Test
    public void testMapToAccount() {
        //Given
        AccountDto accountDto = new AccountDto(1L, new BigDecimal(1500), Currency.EUR, 10L);
        User user = new User(10L, "sebek", "sebek", "919191919", new ArrayList<>());

        when(userRepository.findById(accountDto.getUserId())).thenReturn(java.util.Optional.of(user));
        //When
        Account account = accountMapper.mapToAccount(accountDto);
        //Then
        assertEquals(Currency.EUR, account.getCurrency());
        assertEquals(new BigDecimal(1500), account.getBalance());
    }

    @Test
    public void testMapToAccountDto() {
        //Given
        User user = new User(10L, "sebek", "sebek", "919191919", new ArrayList<>());
        Account account = new Account(1L, new BigDecimal(15000), Currency.GBP, user);
        //When
        AccountDto accountDto = accountMapper.mapToAccountDto(account);
        //Then
        assertEquals(Currency.GBP, accountDto.getCurrency());
        assertEquals(new BigDecimal(15000), accountDto.getBalance());
    }

    @Test
    public void testMapToAccountList() {
        //Given
        AccountDto accountDto1 = new AccountDto(1L, new BigDecimal(1500), Currency.EUR, 10L);
        AccountDto accountDto2 = new AccountDto(2L, new BigDecimal(1800), Currency.EUR, 11L);
        User user1 = new User(10L, "sebek", "sebek", "919191919", new ArrayList<>());
        User user2 = new User(11L, "krzys", "krzys", "949494949", new ArrayList<>());
        List<AccountDto> listDto = new ArrayList<>();
        listDto.add(accountDto1);
        listDto.add(accountDto2);

        when(userRepository.findById(accountDto1.getUserId())).thenReturn(java.util.Optional.of(user1));
        when(userRepository.findById(accountDto2.getUserId())).thenReturn(java.util.Optional.of(user2));
        //When
        List<Account> list = accountMapper.mapToAccountList(listDto);
        //Then
        assertEquals(2, list.size());
        assertEquals("krzys", list.get(1).getUser().getEmail());
        assertEquals(new BigDecimal(1500), list.get(0).getBalance());
    }

    @Test
    public void testMapToAccountDtoList() {
        //Given
        User user1 = new User(10L, "sebek", "sebek", "919191919", new ArrayList<>());
        User user2 = new User(11L, "krzys", "krzys", "949494949", new ArrayList<>());
        Account account1 = new Account(1L, new BigDecimal(1500), Currency.EUR, user1);
        Account account2 = new Account(2L, new BigDecimal(1800), Currency.PLN, user2);
        List<Account> list = new ArrayList<>();
        list.add(account1);
        list.add(account2);
        //When
        List<AccountDto> listDto = accountMapper.mapToAccountDtoList(list);
        //Then
        assertEquals(2, listDto.size());
        assertEquals(Currency.EUR, listDto.get(0).getCurrency());
        assertEquals(Currency.PLN, listDto.get(1).getCurrency());
    }
}
