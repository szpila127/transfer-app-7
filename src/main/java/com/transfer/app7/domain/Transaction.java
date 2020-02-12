package com.transfer.app7.domain;

import lombok.*;
import org.springframework.data.annotation.Immutable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Immutable
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime date;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @OneToOne
    @JoinColumn(name = "ACCOUNT_OUT_ID")
    private Account accountOut;

    @OneToOne
    @JoinColumn(name = "ACCOUNT_IN_ID")
    private Account accountIn;
}
