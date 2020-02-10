package com.transfer.app7.client;

import com.transfer.app7.config.NbpApiConfig;
import com.transfer.app7.domain.NbpAPI.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class NbpApiClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(NbpApiClient.class);

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
