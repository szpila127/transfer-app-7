package com.transfer.app7.mapper.account;

import com.transfer.app7.mapper.AccountMapper;
import com.transfer.app7.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AccountMapperTestSUite {

    @Autowired
    private AccountMapper accountMapper;

    @MockBean
    private UserRepository userRepository;


    @Test
    public void testMapToAccount() {
//        //Given
//        AccountDto accountDto = new AccountDto(1L, new BigDecimal(1500), Currency.EUR, 10L);
//        User user = new User(10L, "sebek", "sebek", "919191919", new ArrayList<>());

//        //When
//        Account account = accountMapper.mapToAccount(accountDto);
//        //Then
//        assertEquals(Currency.EUR, account.getCurrency());
//        assertEquals(new BigDecimal(1500), account.getBalance());
    }
}
