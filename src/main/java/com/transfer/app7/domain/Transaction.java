package com.transfer.app7.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate date;
    private BigDecimal amount;

    @OneToOne
    @JoinColumn(name = "ACCOUNT_OUT_ID")
    private Account accountOut;

    @OneToOne
    @JoinColumn(name = "ACCOUNT_IN_ID")
    private Account accountIn;
}
