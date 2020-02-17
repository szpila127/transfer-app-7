package com.transfer.app7.facade;

import com.transfer.app7.domain.Account;
import com.transfer.app7.domain.Currency;
import com.transfer.app7.domain.User;
import com.transfer.app7.domain.dto.AccountDto;
import com.transfer.app7.domain.dto.UserDto;
import com.transfer.app7.mapper.AccountMapper;
import com.transfer.app7.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountFacadeTestSuite {

    @InjectMocks
    private AccountFacade accountFacade;

    @Mock
    private AppEventFacade appEventFacade;

    @Mock
    private UserFacade userFacade;

    @Mock
    private AccountService accountService;

    @Mock
    private AccountMapper accountMapper;

    @Test
    public void testCreateAccount() {
        //Given
        User user = new User(1L, "sebek", "seb", "919191919", new ArrayList<>());
        Account account = new Account(1L, new BigDecimal(1000), Currency.EUR, user);
        UserDto userDto = new UserDto(1L, "sebek", "seb", "919191919", new ArrayList<>());
        AccountDto accountDto = new AccountDto(1L, new BigDecimal(1000), Currency.EUR, 1L);

        when(accountMapper.mapToAccount(accountDto)).thenReturn(account);
        when(accountService.save(account)).thenReturn(account);
        doNothing().when(appEventFacade).createEvent(any());
        when(userFacade.getUser(any())).thenReturn(userDto);
        //When
        String string = accountFacade.createAccount(accountDto);
        //Then
        assertEquals("Account created.", string);
    }

    @Test
    public void testGetAccounts() {
        //Given
        UserDto userDto = new UserDto(1L, "sebek", "seb", "919191919", new ArrayList<>());
        AccountDto account1Dto = new AccountDto(1L, new BigDecimal(1000), Currency.EUR, 1L);
        AccountDto account2Dto = new AccountDto(2L, new BigDecimal(2000), Currency.PLN, 1L);
        List<AccountDto> listDto = new ArrayList<>();
        listDto.add(account1Dto);
        listDto.add(account2Dto);

        User user = new User(1L, "sebek", "seb", "919191919", new ArrayList<>());
        Account account1 = new Account(1L, new BigDecimal(1000), Currency.EUR, user);
        Account account2 = new Account(2L, new BigDecimal(2000), Currency.PLN, user);
        List<Account> list = new ArrayList<>();
        list.add(account1);
        list.add(account2);

        when(accountMapper.mapToAccountDtoList(any())).thenReturn(listDto);
        when(accountService.getAllAccounts()).thenReturn(list);
        //When
        List<AccountDto> listGet = accountFacade.getAccounts();
        //Then
        assertEquals(2, listGet.size());
    }

    @Test
    public void testCountAccounts() {
        //Given
        when(accountService.countAccounts()).thenReturn(10L);
        //When
        long amount = accountFacade.countAccounts();
        //Then
        assertEquals(10L, amount);
    }

    @Test
    public void testGetAccount() {
        //Given
        AccountDto accountDto = new AccountDto(1L, new BigDecimal(1000), Currency.EUR, 1L);

        User user = new User(1L, "sebek", "seb", "919191919", new ArrayList<>());
        Account account = new Account(1L, new BigDecimal(1000), Currency.EUR, user);

        when(accountService.getAccount(1L)).thenReturn(java.util.Optional.of(account));
        when(accountMapper.mapToAccountDto(account)).thenReturn(accountDto);
        //When
        AccountDto accountGet = accountFacade.getAccount(1L);
        //Then
        assertEquals(Currency.EUR, accountGet.getCurrency());
    }

    @Test
    public void testDeleteAccount() {
        //Given
        User user = new User(1L, "sebek", "seb", "919191919", new ArrayList<>());
        Account account = new Account(1L, new BigDecimal(1000), Currency.EUR, user);
        UserDto userDto = new UserDto(1L, "sebek", "seb", "919191919", new ArrayList<>());

        when(accountService.getAccount(any())).thenReturn(java.util.Optional.of(account));
        when(userFacade.getUser(any())).thenReturn(userDto);
        doNothing().when(appEventFacade).createEvent(any());
        doNothing().when(accountService).deleteAccount(any());
        //When
        String string = accountFacade.deleteAccount(1L);
        //Then
        assertEquals("Account deleted.", string);
    }

    @Test
    public void testUpdateAccount() {
        //Given
        UserDto userDto = new UserDto(1L, "sebek", "seb", "919191919", new ArrayList<>());
        AccountDto account1Dto = new AccountDto(1L, new BigDecimal(1000), Currency.EUR, 1L);

        User user = new User(1L, "sebek", "seb", "919191919", new ArrayList<>());
        Account account = new Account(1L, new BigDecimal(1000), Currency.EUR, user);

        when(accountService.getAccount(any())).thenReturn(java.util.Optional.of(account));
        doNothing().when(appEventFacade).createEvent(any());
        when(accountMapper.mapToAccountDto(any())).thenReturn(account1Dto);
        when(accountMapper.mapToAccount(any())).thenReturn(account);
        when(accountService.save(account)).thenReturn(account);
        //When
        AccountDto accountGet = accountFacade.updateAccount(account1Dto);
        //Then
        assertEquals(Currency.EUR, accountGet.getCurrency());
    }
}
