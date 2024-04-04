package com.bank.service.impl;

import com.bank.entities.Bank;
import com.bank.repository.BankRepository;
import com.bank.service.IBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BankService implements IBankService {

    @Autowired
    private BankRepository bankRepository;

    @Override
    public Bank createBank(Bank bank) {
        if (Objects.isNull(bank.getName()) || Objects.isNull(bank.getCountryCode())) return null;

        Bank updateBank = getBank(bank.getName());
        if (Objects.isNull(updateBank)) bankRepository.save(bank);

        updateBank.setName(bank.getName());
        updateBank.setCountryCode(bank.getCountryCode());
        return bankRepository.save(updateBank);
    }

    @Override
    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    @Override
    public Bank getBank(String bankName) {
        return bankRepository.findByBank(bankName);
    }
}
