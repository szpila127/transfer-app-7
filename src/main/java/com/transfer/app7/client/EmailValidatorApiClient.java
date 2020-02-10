package com.transfer.app7.client;

import com.transfer.app7.config.EmailValidatorApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class EmailValidatorApiClient {

    @Autowired
    private EmailValidatorApiConfig emailValidatorApiConfig;

    @Autowired
    private RestTemplate restTemplate;

    public void validateEmail(String email) throws IOException {

    }
}
