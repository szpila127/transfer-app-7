package com.transfer.app7.domain.dto.emailValidator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailValidatorDto {

    @JsonProperty
    private boolean isValid;
}
