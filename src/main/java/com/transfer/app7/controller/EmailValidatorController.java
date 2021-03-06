package com.transfer.app7.controller;

import com.transfer.app7.client.EmailValidatorApiClient;
import com.transfer.app7.domain.dto.emailValidator.EmailValidatorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/ta7/validate")
public class EmailValidatorController {

    @Autowired
    private EmailValidatorApiClient emailValidatorApiClient;

    @GetMapping(value = "/{email}")
    public EmailValidatorDto getValidation(@PathVariable String email) {
        return emailValidatorApiClient.validateEmail(email);
    }
}
