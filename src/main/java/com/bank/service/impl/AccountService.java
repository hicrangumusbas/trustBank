package com.bank.service.impl;

import com.bank.entities.Account;
import com.bank.enumeration.AccountFilterType;
import com.bank.repository.AccountRepository;
import com.bank.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account getAccount(Long bankId, AccountFilterType accountType, Long filterValue) {
        if (Objects.isNull(bankId) || Objects.isNull(accountType) || Objects.isNull(filterValue)) return null;

        List<Account> accounts = accountRepository.findAccountsBankFilter(bankId, accountType.getType(), filterValue);
        return accounts.isEmpty() ? null : accounts.get(0);
    }

    @Override
    public List<Account> getOwnerAccounts(AccountFilterType accountType, Long filterValue) {
        if (Objects.isNull(accountType) || Objects.isNull(filterValue)) return null;

        return accountRepository.findAccountsByFilter(accountType.getType(), filterValue);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account createAccount(Account account) {
        if (Objects.isNull(account.getIdentificationNumber()) || Objects.isNull(account.getAccountNumber()) || Objects.isNull(account.getBankId()))
            return null;

        Account updateAccount = getAccount(account.getBankId(), AccountFilterType.ACCOUNT_NUMBER, account.getAccountNumber());
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
}
