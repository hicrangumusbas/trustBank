package com.bank.service;

import com.bank.entities.Account;
import com.bank.entities.Transaction;
import com.bank.enumeration.AccountFilterType;
import com.bank.enumeration.TransactionType;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    public List<Transaction> getTransactionHistory(TransactionType type, Long accountNumber) {
        if (Objects.isNull(accountNumber)) return null;

        int typeValue = Objects.isNull(type) ? -1 : type.getValue();
        return transactionRepository.findByAccountNumber(typeValue, accountNumber);
    }

    public Account depositMoney(Long bankId, AccountFilterType accountType, Long filterValue, double amount) {
        if (Objects.isNull(bankId) || Objects.isNull(accountType) || Objects.isNull(filterValue)) return null;

        Account account = accountService.getAccount(bankId, accountType, filterValue);
        if (!Objects.isNull(account)) return transaction(account, amount, true);
        return null;
    }

    public Account withdrawMoney(Long bankId, AccountFilterType accountType, Long filterValue, double amount) {
        if (Objects.isNull(bankId) || Objects.isNull(accountType) || Objects.isNull(filterValue)) return null;

        Account account = accountService.getAccount(bankId, accountType, filterValue);
        if (!Objects.isNull(account)) {
            if (account.getBalance() < amount)
                throw new ArithmeticException("There is not enough money in your account.");

            return transaction(account, amount, false);
        }
        return null;
    }

    private Account transaction(Account account, double amount, Boolean deposit) {
        Transaction transaction = new Transaction();
        transaction.setAccountNumber(account.getAccountNumber());
        transaction.setAmount(amount);
        transaction.setTransactionDate(System.currentTimeMillis());
        transaction.setTransactionType(deposit ? TransactionType.DEPOSIT.getValue() : TransactionType.WITHDRAW.getValue());
        transaction.setSuccess(true);
        transactionRepository.save(transaction);

        synchronized (account) {
            account = accountRepository.getById(account.getId());
            if (deposit) account.setBalance(account.getBalance() + amount);
            else account.setBalance(account.getBalance() - amount);
        }
        return accountRepository.save(account);
    }
}
