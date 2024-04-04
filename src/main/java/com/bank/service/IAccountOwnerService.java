package com.bank.service;

import com.bank.entities.AccountOwner;

import java.util.List;

public interface IAccountOwnerService {

    AccountOwner getAccountOwner(Long identificationNumber);

    List<AccountOwner> getAllAccountOwners();

    AccountOwner createAccountOwner(AccountOwner owner);
}
