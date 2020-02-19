package com.transfer.app7.domain.dto;

import com.transfer.app7.domain.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private long id;
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private long userId;

}
