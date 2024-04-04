package com.bank.service;

import com.bank.entities.Account;
import com.bank.entities.Transaction;
import com.bank.model.TransactionFilterDTO;

import java.util.List;

public interface ITransactionService {

    List<Transaction> getTransactionHistory(TransactionFilterDTO transaction);

    Account transaction(TransactionFilterDTO transaction, Boolean deposit);

}
