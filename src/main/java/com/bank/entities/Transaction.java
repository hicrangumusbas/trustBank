package com.bank.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountNumber;
    private Long transactionDate;
    private double amount;
    private int transactionType;
    private boolean success;

    public Transaction() {
    }

    public Transaction(Long id, Long accountNumber, Long transactionDate, double amount, int transactionType) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.transactionType = transactionType;
    }
}
