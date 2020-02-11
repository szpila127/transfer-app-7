package com.transfer.app7.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

    private Long id;
    private LocalDateTime date = LocalDateTime.now();
    private BigDecimal amount;
    private Long accountOutId;
    private Long accountInId;
}
