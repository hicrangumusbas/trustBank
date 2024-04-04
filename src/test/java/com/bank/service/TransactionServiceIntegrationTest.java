package com.bank.service;

import com.bank.entities.Account;
import com.bank.entities.Transaction;
import com.bank.enumeration.TransactionType;
import com.bank.model.AccountFilterDTO;
import com.bank.model.TransactionFilterDTO;
import com.bank.util.ConcurrentManager;
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
    private ITransactionService transactionService;

    @Autowired
    private IAccountService accountService;

    @Test
    @DirtiesContext
    void transactionHistory() {
        Account account = accountService.getAccount(new AccountFilterDTO(3L, 3333333333L));
        if (Objects.isNull(account)) return;

        List<Transaction> transactionsAll = transactionService.getTransactionHistory(new TransactionFilterDTO(null, account.getAccountNumber(), null, TransactionType.ALL.getValue()));
        List<Transaction> transactionsFilter = transactionService.getTransactionHistory(new TransactionFilterDTO(null, account.getAccountNumber(), null, TransactionType.DEPOSIT.getValue()));
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

        Account account = accountService.getAccount(new AccountFilterDTO(3L, 3333333333L));
        if (Objects.isNull(account)) return;

        double depositAmount = 1500.0;
        Account updatedAccount = transactionService.transaction(new
                TransactionFilterDTO(account.getBankId(), account.getAccountNumber(), depositAmount, TransactionType.ALL.getValue()), true);

        Assertions.assertNotNull(updatedAccount);
        Assertions.assertEquals(account.getBalance() + depositAmount, updatedAccount.getBalance());
    }

    @Test
    @DirtiesContext
    void withdrawMoneyTest() throws Exception {
        // Account newAccount = new Account();
        // newAccount.setBankId(2L);
        // newAccount.setAccountNumber(123456789L);
        // newAccount.setIdentificationNumber(987654321L);
        // newAccount.setBalance(1000.0);

        // Account savedAccount = accountService.createAccount(newAccount);

        Account account = accountService.getAccount(new AccountFilterDTO(3L, 3333333333L));
        if (Objects.isNull(account)) return;

        double withdrawAmount = 50.0;
        if (account.getBalance() < withdrawAmount)
            throw new ArithmeticException("There is not enough money in your account.");

        Account updatedAccount = transactionService.transaction(new
                TransactionFilterDTO(account.getBankId(), account.getAccountNumber(), withdrawAmount, TransactionType.ALL.getValue()), false);

        Assertions.assertNotNull(updatedAccount);
        Assertions.assertEquals(account.getBalance() - withdrawAmount, updatedAccount.getBalance());
    }

    @Test
    @DirtiesContext
    void depositMoneyTestAsync() throws InterruptedException {
        Account account = accountService.getAccount(new AccountFilterDTO(3L, 3333333333L));
        if (Objects.isNull(account)) return;

        double depositAmount = 1500.0;
        int threadCount = 5;

        ConcurrentManager concurrentManager = new ConcurrentManager();
        concurrentManager.processConcurrently(threadCount, () -> {
            transactionService.transaction(new
                    TransactionFilterDTO(account.getBankId(), account.getAccountNumber(), depositAmount, TransactionType.ALL.getValue()), true);
        });

        double expectedBalance = account.getBalance() + (depositAmount * threadCount);
        Account finalAccount = accountService.getAccount(new AccountFilterDTO(account.getBankId(), account.getAccountNumber()));
        Assertions.assertNotNull(finalAccount);
        Assertions.assertEquals(expectedBalance, finalAccount.getBalance());
    }
}
