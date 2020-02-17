package com.transfer.app7.controller;

import com.transfer.app7.client.NbpApiClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CurrencyController.class)
public class CurrencyControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NbpApiClient nbpApiClient;

    @Test
    public void testGetFactor() throws Exception {
        //Given
        double factor = 2.04;

        when(nbpApiClient.getCurrencyFactor("CURRENCY")).thenReturn(factor);

        //When & Then
        mockMvc.perform(get("/v1/ta7/currency/CURRENCY")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(2.04)));
    }

}
