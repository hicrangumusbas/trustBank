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
class AccountServiceIntegrationTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Test
    @DirtiesContext
    void Account() {
        Account account = accountService.getAccount(1L, AccountFilterType.ID,2L);
        Assertions.assertNotNull(account);
    }

    @Test
    @DirtiesContext
    void AllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        Assertions.assertNotNull(accounts);
    }

    @Test
    @DirtiesContext
    void transactionHistory() {
        Account account = accountService.getAccount(2L, AccountFilterType.ID,2L);
        if(Objects.isNull(account)) return;

        List<Transaction> transactionsAll = transactionService.getTransactionHistory(TransactionType.ALL, account.getAccountNumber());
        List<Transaction> transactionsFilter = transactionService.getTransactionHistory(TransactionType.DEPOSIT, account.getAccountNumber());
        Assertions.assertNotNull(transactionsAll);
        Assertions.assertNotNull(transactionsFilter);
    }

    @Test
    @DirtiesContext
    void createAccountTest() {
        Account newAccount = new Account();
        newAccount.setBankId(2L);
        newAccount.setAccountNumber(123236789L);
        newAccount.setIdentificationNumber(982354321L);
        newAccount.setBalance(100000.90);

        Account savedAccount = accountService.createAccount(newAccount);

        Assertions.assertNotNull(savedAccount.getId());

        Account retrievedAccount = accountService.getAccount(savedAccount.getBankId(), AccountFilterType.ID,savedAccount.getId());
        Assertions.assertEquals(newAccount.getBankId(), retrievedAccount.getBankId());
        Assertions.assertEquals(newAccount.getAccountNumber(), retrievedAccount.getAccountNumber());
        Assertions.assertEquals(newAccount.getIdentificationNumber(), retrievedAccount.getIdentificationNumber());
        Assertions.assertEquals(newAccount.getBalance(), retrievedAccount.getBalance());
    }

    @Test
    @DirtiesContext
    void checkBalanceTest() {
        Account account = accountService.getAccount(2L, AccountFilterType.ID,6L);
        if(Objects.isNull(account)) return;

        Double balance = accountService.checkBalance(account.getId());

        Assertions.assertNotNull(balance);
        Assertions.assertEquals(account.getBalance(), balance);
    }
}