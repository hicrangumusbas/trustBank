package com.bank.service;

import com.bank.entities.Account;
import com.bank.enumeration.AccountFilterType;
import com.bank.repository.AccountRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getAccount(Long bankId, AccountFilterType accountType, Long filterValue) {
        if (Objects.isNull(bankId) || Objects.isNull(accountType) || Objects.isNull(filterValue)) return null;

        List<Account> accounts = accountRepository.findAccountsBankFilter(bankId, accountType.getType(), filterValue);
        return accounts.isEmpty() ? null : accounts.get(0);
    }

    public List<Account> getOwnerAccounts(AccountFilterType accountType, Long filterValue) {
        if (Objects.isNull(accountType) || Objects.isNull(filterValue)) return null;

        return accountRepository.findAccountsByFilter(accountType.getType(), filterValue);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account createAccount(Account account) {
        if (Objects.isNull(account.getIdentificationNumber()) || Objects.isNull(account.getAccountNumber()) || Objects.isNull(account.getBankId()))
            return null;

        Account updateAccount = (Account) getAccount(account.getBankId(), AccountFilterType.ACCOUNT_NUMBER, account.getAccountNumber());
        if (Objects.isNull(updateAccount)) return accountRepository.save(account);

        updateAccount.setBankId(account.getBankId());
        updateAccount.setBalance(account.getBalance());
        return accountRepository.save(updateAccount);
    }

    public Double checkBalance(Long accountId) {
        if (Objects.isNull(accountId)) return null;

        Account account = accountRepository.findById(accountId).orElse(null);
        return account != null ? account.getBalance() : null;
    }
}