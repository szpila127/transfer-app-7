package com.transfer.app7.controller;

import com.google.gson.Gson;
import com.transfer.app7.domain.Currency;
import com.transfer.app7.domain.dto.TransactionDto;
import com.transfer.app7.facade.TransactionFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionFacade transactionFacade;

    @Test
    public void testCreateTransaction() throws Exception {
        //Given
        TransactionDto transactionDto = new TransactionDto(1L, LocalDateTime.parse("2010-02-17T12:58:05"), new BigDecimal(1000), Currency.EUR, 11L, 12L);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(transactionDto);

        //When & Then
        mockMvc.perform(post("/v1/ta7/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());


    }
}
