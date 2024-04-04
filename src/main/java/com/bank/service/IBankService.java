package com.bank.service;

import com.bank.entities.Bank;

import java.util.List;

public interface IBankService {

    Bank createBank(Bank bank);

    List<Bank> getAllBanks();

    Bank getBank(String bankName);
}
