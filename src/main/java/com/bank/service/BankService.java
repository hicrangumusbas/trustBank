package com.bank.service;

import com.bank.entities.Bank;
import com.bank.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BankService {

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public Bank createBank(Bank bank) {
        if (Objects.isNull(bank.getName()) || Objects.isNull(bank.getCountryCode())) return null;

        Bank updateBank = getBank(bank.getName());
        if (Objects.isNull(updateBank)) bankRepository.save(bank);

        updateBank.setName(bank.getName());
        updateBank.setCountryCode(bank.getCountryCode());
        return bankRepository.save(updateBank);
    }

    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    public Bank getBank(String bankName) {
        return bankRepository.findByBank(bankName);
    }
}
