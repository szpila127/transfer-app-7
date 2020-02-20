package com.transfer.app7.controller;

import com.transfer.app7.client.EmailValidatorApiClient;
import com.transfer.app7.domain.dto.emailValidator.EmailValidatorDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmailValidatorController.class)
public class EmailValidatorControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailValidatorApiClient emailValidatorApiClient;

    @Test
    public void testGetValidation() throws Exception {
        //Given
        EmailValidatorDto emailValidatorDto = new EmailValidatorDto(true);

        when(emailValidatorApiClient.validateEmail("szpila127@wp.pl")).thenReturn(emailValidatorDto);

        //When & Then
        mockMvc.perform(get("/v1/ta7/validate/szpila127@wp.pl")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isValid", is(true)));
    }
}
