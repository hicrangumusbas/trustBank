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
        int typeValue = Objects.isNull(type) ? -1 : type.getValue();
        return transactionRepository.findByAccountNumber(typeValue, accountNumber);
    }

    public Account depositMoney(Long bankId, AccountFilterType accountType, Long filterValue, double amount) {
        Account account = accountService.getAccount(bankId, accountType, filterValue);

        if (!Objects.isNull(account)) {
            synchronized (account) {
                Transaction transaction = new Transaction();
                transaction.setAccountNumber(account.getAccountNumber());
                transaction.setAmount(amount);
                transaction.setTransactionDate(System.currentTimeMillis());
                transaction.setTransactionType(TransactionType.DEPOSIT.getValue());

                transactionRepository.save(transaction);

                account.setBalance(account.getBalance() + amount);
                return accountRepository.save(account);
            }
        }
        return null;
    }

    public Account withdrawMoney(Long bankId, AccountFilterType accountType, Long filterValue, double amount) {
        Account account = accountService.getAccount(bankId, accountType, filterValue);

        if (!Objects.isNull(account) && account.getBalance() >= amount) {
            synchronized (account) {
                Transaction transaction = new Transaction();
                transaction.setAccountNumber(account.getAccountNumber());
                transaction.setAmount(amount);
                transaction.setTransactionDate(System.currentTimeMillis());
                transaction.setTransactionType(TransactionType.WITHDRAW.getValue());

                transactionRepository.save(transaction);

                account.setBalance(account.getBalance() - amount);
                return accountRepository.save(account);
            }
        }
        return null;
    }
}
