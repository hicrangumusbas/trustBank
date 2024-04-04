package com.bank.service;

import com.bank.entities.Account;
import com.bank.enumeration.AccountFilterType;

import java.util.List;

public interface IAccountService {

    Account getAccount(Long bankId, AccountFilterType accountType, Long filterValue);

    List<Account> getOwnerAccounts(AccountFilterType accountType, Long filterValue);

    List<Account> getAllAccounts();

    Account createAccount(Account account);

    Double checkBalance(Long accountId);
}
