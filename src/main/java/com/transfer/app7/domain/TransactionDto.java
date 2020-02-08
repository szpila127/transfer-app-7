package com.transfer.app7.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

    private Long id;
    private LocalDate date;
    private BigDecimal amount;
    private Long accountOutId;
    private Long accountInId;
}
