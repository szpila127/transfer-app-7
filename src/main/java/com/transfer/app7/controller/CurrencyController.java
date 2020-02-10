package com.transfer.app7.controller;

import com.transfer.app7.client.NbpApiClient;
import com.transfer.app7.domain.NbpAPI.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/ta7/currency")
public class CurrencyController {

    @Autowired
    NbpApiClient client;

    @GetMapping(value = "/{code}")
    public double getFactor(@PathVariable String code) {
        code = code.toLowerCase();
        if (code.equals("pln")) {
            return 1;
        } else {
            ResponseDto responseDto = client.getCurrencyFactor(code);
            return responseDto.getRates()[0].getMid();
        }
    }
}
