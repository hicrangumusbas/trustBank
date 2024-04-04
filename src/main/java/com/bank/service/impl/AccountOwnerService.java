package com.bank.service.impl;

import com.bank.entities.AccountOwner;
import com.bank.repository.AccountOwnerRepository;
import com.bank.service.IAccountOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AccountOwnerService implements IAccountOwnerService {

    @Autowired
    private AccountOwnerRepository accountOwnerRepository;

    @Override
    public AccountOwner getAccountOwner(Long identificationNumber) {
        if (Objects.isNull(identificationNumber)) return null;

        return accountOwnerRepository.findByAccountOwner(identificationNumber);
    }

    @Override
    public List<AccountOwner> getAllAccountOwners() {
        return accountOwnerRepository.findAll();
    }

    @Override
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
