package com.bank.service;

import com.bank.entities.Account;
import com.bank.entities.Bank;
import com.bank.repository.AccountRepository;
import com.bank.repository.BankRepository;
import com.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public Bank createBank(Bank bank) {
        return bankRepository.save(bank);
    }

    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }
}
