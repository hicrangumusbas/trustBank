package com.bank.service;

import com.bank.entities.Account;
import com.bank.model.AccountFilterDTO;

import java.util.List;

public interface IAccountService {

    Account getAccount(AccountFilterDTO account);

    List<Account> getOwnerAccounts(AccountFilterDTO account);

    List<Account> getAllAccounts();

    Account createAccount(Account account);

    Double checkBalance(Long accountId);

    List<Account> findAccountsByFilter(AccountFilterDTO account, Boolean accountNumber);
}
