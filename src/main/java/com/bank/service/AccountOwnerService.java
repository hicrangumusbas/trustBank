package com.bank.service;

import com.bank.entities.Account;
import com.bank.entities.AccountOwner;
import com.bank.enumeration.AccountFilterType;
import com.bank.repository.AccountOwnerRepository;
import com.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountOwnerService {
    @Autowired
    private AccountOwnerRepository accountOwnerRepository;

    @Autowired
    public AccountOwnerService(AccountOwnerRepository accountOwnerRepository) {
        this.accountOwnerRepository = accountOwnerRepository;
    }

    public AccountOwner getAccountOwner(Long identificationNumber) {
        return accountOwnerRepository.findByAccountOwner(identificationNumber);
    }

    public List<AccountOwner> getAllAccountOwners() {
        return accountOwnerRepository.findAll();
    }

    public AccountOwner createAccountOwner(AccountOwner accountOwner) {
        return accountOwnerRepository.save(accountOwner);
    }

}
