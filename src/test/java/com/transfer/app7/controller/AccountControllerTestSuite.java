package com.transfer.app7.controller;

import com.google.gson.Gson;
import com.transfer.app7.domain.Currency;
import com.transfer.app7.domain.dto.AccountDto;
import com.transfer.app7.facade.AccountFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountFacade accountFacade;

    @Test
    public void testCreateAccount() throws Exception {
        //Given
        AccountDto accountDto = new AccountDto(1L, new BigDecimal(15000), Currency.PLN, 11L);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(accountDto);

        //When & Then
        mockMvc.perform(post("/v1/ta7/account")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetEmptyAccountsLits() throws Exception {
        //Given
        List<AccountDto> listDto = new ArrayList<>();

        when(accountFacade.getAccounts()).thenReturn(listDto);

        //When & Then
        mockMvc.perform(get("/v1/ta7/account").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testGetAccountsList() throws Exception {
        //Given
        AccountDto accountDto1 = new AccountDto(1L, new BigDecimal(1000), Currency.EUR, 11L);
        AccountDto accountDto2 = new AccountDto(2L, new BigDecimal(2000), Currency.GBP, 12L);
        List<AccountDto> listDto = new ArrayList<>();
        listDto.add(accountDto1);
        listDto.add(accountDto2);

        when(accountFacade.getAccounts()).thenReturn(listDto);

        //When & Then
        mockMvc.perform(get("/v1/ta7/account").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].currency", is(Currency.GBP.toString())));
    }

    @Test
    public void testCountAccounts() {

    }

}
