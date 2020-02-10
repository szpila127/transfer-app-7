package com.transfer.app7.client;

import com.transfer.app7.config.NbpApiConfig;
import com.transfer.app7.domain.nbpAPI.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class NbpApiClient {

    @Autowired
    private NbpApiConfig nbpApiConfig;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseDto getCurrencyFactor(String currencyCode) {
        URI url = UriComponentsBuilder.fromHttpUrl(nbpApiConfig.getNbpApiEndpoint() + currencyCode)
                .build()
                .encode()
                .toUri();
        ResponseDto response = restTemplate.getForObject(url, ResponseDto.class);
        if (response != null) {
            return response;
        } else return new ResponseDto();
    }
}
