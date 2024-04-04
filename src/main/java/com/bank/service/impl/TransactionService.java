package com.bank.service.impl;

import com.bank.entities.Account;
import com.bank.entities.Transaction;
import com.bank.enumeration.TransactionType;
import com.bank.model.TransactionFilterDTO;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import com.bank.service.IAccountService;
import com.bank.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class TransactionService implements ITransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private IAccountService accountService;

    @Override
    public List<Transaction> getTransactionHistory(TransactionFilterDTO transaction) {
        return findTransactionByFilter(transaction);
    }

    @Override
    public Account transaction(TransactionFilterDTO transaction, Boolean deposit) {
        if (Objects.isNull(transaction) || transaction.isNullFieldExist()) return null;

        Account account = accountService.getAccount(TransactionFilterDTO.fromAccount(transaction));
        if (!Objects.isNull(account)) return transaction(account, transaction.getAmount(), deposit);
        return null;
    }

    private Account transaction(Account account, double amount, Boolean deposit) {
        if (!deposit && account.getBalance() < amount)
            throw new ArithmeticException("There is not enough money in your account.");

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


    List<Transaction> findTransactionByFilter(TransactionFilterDTO transaction) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transaction> criteriaQuery = criteriaBuilder.createQuery(Transaction.class);
        Root<Transaction> transactionRoot = criteriaQuery.from(Transaction.class);

        Predicate bankIdPredicate = criteriaBuilder.equal(transactionRoot.get("accountNumber"), transaction.getAccountNumber());
        Predicate filterPredicate = null;

        if (TransactionType.DEPOSIT.getValue().equals(transaction.getTransactionType()) ||
                TransactionType.WITHDRAW.getValue().equals(transaction.getTransactionType()))
            filterPredicate = criteriaBuilder.equal(transactionRoot.get("transactionType"), transaction.getTransactionType());

        Predicate finalPredicate = criteriaBuilder.and(bankIdPredicate, filterPredicate);
        criteriaQuery.select(transactionRoot).where(finalPredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }}
