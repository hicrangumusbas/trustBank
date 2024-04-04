package com.bank.service;

import com.bank.entities.Account;
import com.bank.entities.Transaction;
import com.bank.enumeration.AccountFilterType;
import com.bank.enumeration.TransactionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Objects;

@SpringBootTest
public class TransactionServiceIntegrationTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @Test
    @DirtiesContext
    void transactionHistory() {
        Account account = accountService.getAccount(2L,AccountFilterType.ID,2L);
        if(Objects.isNull(account)) return;

        List<Transaction> transactionsAll = transactionService.getTransactionHistory(TransactionType.ALL, account.getAccountNumber());
        List<Transaction> transactionsFilter = transactionService.getTransactionHistory(TransactionType.DEPOSIT, account.getAccountNumber());
        Assertions.assertNotNull(transactionsAll);
        Assertions.assertNotNull(transactionsFilter);
    }

    @Test
    @DirtiesContext
    void depositMoneyTest() {
        // Account newAccount = new Account();
        // newAccount.setBankId(2L);
        // newAccount.setAccountNumber(123456789L);
        // newAccount.setIdentificationNumber(987654321L);
        // newAccount.setBalance(1000.0);

        // Account savedAccount = accountService.createAccount(newAccount);

        Account account = accountService.getAccount(2L, AccountFilterType.ID,2L);
        if(Objects.isNull(account)) return;

        double depositAmount = 1500.0;
        Account updatedAccount = transactionService.depositMoney(account.getBankId(), AccountFilterType.ID,account.getId(), depositAmount);

        Assertions.assertNotNull(updatedAccount);
        Assertions.assertEquals(account.getBalance() + depositAmount, updatedAccount.getBalance());
    }

    @Test
    @DirtiesContext
    void withdrawMoneyTest() {
        // Account newAccount = new Account();
        // newAccount.setBankId(2L);
        // newAccount.setAccountNumber(123456789L);
        // newAccount.setIdentificationNumber(987654321L);
        // newAccount.setBalance(1000.0);

        // Account savedAccount = accountService.createAccount(newAccount);

        Account account = accountService.getAccount(2L, AccountFilterType.ID,2L);
        if(Objects.isNull(account)) return;

        double withdrawAmount = 600.0;
        Account updatedAccount = transactionService.withdrawMoney(account.getBankId(), AccountFilterType.ID,account.getId(), withdrawAmount);

        Assertions.assertNotNull(updatedAccount);
        Assertions.assertEquals(account.getBalance() - withdrawAmount, updatedAccount.getBalance());
    }

}