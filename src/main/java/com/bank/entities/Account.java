package com.bank.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bankId;
    private Long accountNumber;
    private Long identificationNumber;
    private Double balance;

    public Account() {
    }

    public Account(Long id, Long bankId, Long accountNumber, Long identificationNumber, double balance) {
        this.id = id;
        this.bankId = bankId;
        this.accountNumber = accountNumber;
        this.identificationNumber = identificationNumber;
        this.balance = balance;
    }
}
