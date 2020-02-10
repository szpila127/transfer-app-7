package com.transfer.app7.controller;

import com.transfer.app7.client.EmailValidatorApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/v1/ta7/validate")
public class EmailValidatorController {

    @Autowired
    private EmailValidatorApiClient emailValidatorApiClient;

    @GetMapping(value = "/{email}")
    public void getValidation(@PathVariable String email) throws IOException {
        emailValidatorApiClient.validateEmail(email);
    }
}
