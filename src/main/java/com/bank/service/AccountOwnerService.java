package com.bank.service;

import com.bank.entities.AccountOwner;
import com.bank.repository.AccountOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AccountOwnerService {

    @Autowired
    private AccountOwnerRepository accountOwnerRepository;

    @Autowired
    public AccountOwnerService(AccountOwnerRepository accountOwnerRepository) {
        this.accountOwnerRepository = accountOwnerRepository;
    }

    public AccountOwner getAccountOwner(Long identificationNumber) {
        if (Objects.isNull(identificationNumber)) return null;

        return accountOwnerRepository.findByAccountOwner(identificationNumber);
    }

    public List<AccountOwner> getAllAccountOwners() {
        return accountOwnerRepository.findAll();
    }

    public AccountOwner createAccountOwner(AccountOwner owner) {
        if (Objects.isNull(owner) || Objects.isNull(owner.getIdentificationNumber()) ||
                Objects.isNull(owner.getFirstName()) || Objects.isNull(owner.getLastName()) ||
                Objects.isNull(owner.getContactNumber())) return null;

        AccountOwner updateOwner = getAccountOwner(owner.getIdentificationNumber());
        if (Objects.isNull(updateOwner)) return accountOwnerRepository.save(owner);

        updateOwner.setFirstName(owner.getFirstName());
        updateOwner.setLastName(owner.getLastName());
        updateOwner.setContactNumber(owner.getContactNumber());
        updateOwner.setMailAddress(owner.getMailAddress());
        return accountOwnerRepository.save(updateOwner);
    }

}
