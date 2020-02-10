package com.transfer.app7.controller;

import com.transfer.app7.client.NbpApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/ta7/currency")
public class CurrencyController {

    @Autowired
    NbpApiClient nbpApiClient;

    @GetMapping(value = "/{code}")
    public double getFactor(@PathVariable String code) {
        return nbpApiClient.getCurrencyFactor(code);
    }
}

