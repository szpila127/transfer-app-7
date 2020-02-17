package com.transfer.app7.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.transfer.app7.config.LocalDateAdapter;
import com.transfer.app7.domain.Currency;
import com.transfer.app7.domain.dto.TransactionDto;
import com.transfer.app7.facade.TransactionFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                .create();
        String jsonContent = gson.toJson(transactionDto);

        //When & Then
        mockMvc.perform(post("/v1/ta7/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void testReturnTransaction() throws Exception {
        //Given
        when(transactionFacade.returnTransaction(10L)).thenReturn("Returned");

        //When & Then
        mockMvc.perform(get("/v1/ta7/transaction/10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTransactions() throws Exception {
        //Given
        TransactionDto transactionDto1 = new TransactionDto(1L, LocalDateTime.parse("2010-02-17T12:58:05"), new BigDecimal(1000), Currency.EUR, 11L, 12L);
        TransactionDto transactionDto2 = new TransactionDto(2L, LocalDateTime.parse("2010-02-17T12:58:05"), new BigDecimal(1500), Currency.GBP, 13L, 14L);
        List<TransactionDto> listDto = new ArrayList<>();
        listDto.add(transactionDto1);
        listDto.add(transactionDto2);

        when(transactionFacade.getTransactions()).thenReturn(listDto);

        //When & Then
        mockMvc.perform(get("/v1/ta7/transaction")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].currency", is(Currency.GBP.toString())));
    }

    @Test
    public void testCountTransactions() throws Exception {
        //Given
        when(transactionFacade.countTransactions()).thenReturn(11L);

        //When & Then
        mockMvc.perform(get("/v1/ta7/transaction/count")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(11)));
    }

    @Test
    public void testGetTransaction() throws Exception {
        //Given
        TransactionDto transactionDto1 = new TransactionDto(1L, LocalDateTime.parse("2010-02-17T12:58:05"), new BigDecimal(1000), Currency.EUR, 11L, 12L);

        when(transactionFacade.getTransaction(1L)).thenReturn(transactionDto1);

        //When & Then
        mockMvc.perform(get("/v1/ta7/transaction/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currency", is(Currency.EUR.toString())));
    }

    @Test
    public void testDeleteTransaction() throws Exception {
        //Given
        doNothing().when(transactionFacade).deleteTransaction(10L);

        //When & Then
        mockMvc.perform(delete("/v1/ta7/transaction/10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(transactionFacade, times(1)).deleteTransaction(10L);
    }

    @Test
    public void testUpdateTransaction() throws Exception {
        //Given
        TransactionDto transactionDto1 = new TransactionDto(1L, LocalDateTime.parse("2010-02-17T12:58:05"), new BigDecimal(1000), Currency.EUR, 11L, 12L);

        when(transactionFacade.updateTransaction(ArgumentMatchers.any(TransactionDto.class))).thenReturn(transactionDto1);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                .create();
        String jsonContent = gson.toJson(transactionDto1);

        //When & Then
        mockMvc.perform(put("/v1/ta7/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currency", is(Currency.EUR.toString())));
    }
}
