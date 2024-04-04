package com.bank.service.impl;

import com.bank.entities.Account;
import com.bank.model.AccountFilterDTO;
import com.bank.repository.AccountRepository;
import com.bank.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Account getAccount(AccountFilterDTO account) {
        if (Objects.isNull(account.getAccountNumber())) return null;

        List<Account> accounts = findAccountsByFilter(account, true);
        return accounts.isEmpty() ? null : accounts.get(0);
    }

    @Override
    public List<Account> getOwnerAccounts(AccountFilterDTO account) {
        if (Objects.isNull(account.getAccountNumber())) return null;

        return findAccountsByFilter(account, true);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account createAccount(Account account) {
        if (Objects.isNull(account.getAccountNumber())) return null;

        Account updateAccount = getAccount(AccountFilterDTO.fromAccount(account));
        if (Objects.isNull(updateAccount)) return accountRepository.save(account);

        updateAccount.setBankId(account.getBankId());
        updateAccount.setBalance(account.getBalance());
        return accountRepository.save(updateAccount);
    }

    @Override
    public Double checkBalance(Long accountId) {
        if (Objects.isNull(accountId)) return null;

        Account account = accountRepository.findById(accountId).orElse(null);
        return account != null ? account.getBalance() : null;
    }


    @Override
    public List<Account> findAccountsByFilter(AccountFilterDTO account, Boolean accountNumber) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
        Root<Account> accountRoot = criteriaQuery.from(Account.class);

        Predicate bankIdPredicate = criteriaBuilder.equal(accountRoot.get("bankId"), account.getBankId());
        Predicate filterPredicate;

        if (accountNumber)
            filterPredicate = criteriaBuilder.equal(accountRoot.get("accountNumber"), account.getAccountNumber());
        else
            filterPredicate = criteriaBuilder.equal(accountRoot.get("identificationNumber"), account.getIdentificationNumber());

        Predicate finalPredicate = criteriaBuilder.and(bankIdPredicate, filterPredicate);
        criteriaQuery.select(accountRoot).where(finalPredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
