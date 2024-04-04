package com.bank.service;

import com.bank.entities.Account;
import com.bank.entities.Transaction;
import com.bank.enumeration.AccountFilterType;
import com.bank.enumeration.TransactionType;

import java.util.List;

public interface ITransactionService {

    List<Transaction> getTransactionHistory(TransactionType type, Long accountNumber);

    Account depositMoney(Long bankId, AccountFilterType accountType, Long filterValue, double amount);

    Account withdrawMoney(Long bankId, AccountFilterType accountType, Long filterValue, double amount);

}
