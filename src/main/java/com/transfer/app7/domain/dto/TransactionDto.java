package com.transfer.app7.domain.dto;

import com.transfer.app7.domain.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

    private long id;
    private LocalDateTime date = LocalDateTime.now();
    private BigDecimal amount;
    private Currency currency;
    private long accountOutId;
    private long accountInId;
}
