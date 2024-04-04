package com.bank.service;

import com.bank.entities.Account;
import com.bank.entities.Transaction;
import com.bank.enumeration.TransactionType;
import com.bank.model.AccountFilterDTO;
import com.bank.model.TransactionFilterDTO;
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
    private IAccountService accountService;

    @Autowired
    private ITransactionService transactionService;

    @Test
    @DirtiesContext
    void Account() {
        Account account = accountService.getAccount(new AccountFilterDTO(3L,3333333333L));
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
        Account account = accountService.getAccount(new AccountFilterDTO(3L,3333333333L));
        if(Objects.isNull(account)) return;

        List<Transaction> transactionsAll = transactionService.getTransactionHistory(new TransactionFilterDTO(null, account.getAccountNumber(), null, TransactionType.ALL.getValue()));
        List<Transaction> transactionsFilter = transactionService.getTransactionHistory(new TransactionFilterDTO(null, account.getAccountNumber(), null, TransactionType.DEPOSIT.getValue()));
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

        Account retrievedAccount = accountService.getAccount(new AccountFilterDTO(savedAccount.getBankId(), savedAccount.getAccountNumber()));
        Assertions.assertEquals(newAccount.getBankId(), retrievedAccount.getBankId());
        Assertions.assertEquals(newAccount.getAccountNumber(), retrievedAccount.getAccountNumber());
        Assertions.assertEquals(newAccount.getIdentificationNumber(), retrievedAccount.getIdentificationNumber());
        Assertions.assertEquals(newAccount.getBalance(), retrievedAccount.getBalance());
    }

    @Test
    @DirtiesContext
    void checkBalanceTest() {
        Account account = accountService.getAccount(new AccountFilterDTO(3L,3333333333L));
        if(Objects.isNull(account)) return;

        Double balance = accountService.checkBalance(account.getId());

        Assertions.assertNotNull(balance);
        Assertions.assertEquals(account.getBalance(), balance);
    }
}