package com.bank.service;

import com.bank.entities.Bank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BankServiceIntegrationTest {

    @Autowired
    private IBankService bankService;

    @Test
    @DirtiesContext
    void AllBanks() {
        List<Bank> banks = bankService.getAllBanks();
        Assertions.assertNotNull(banks);
    }

    @Test
    void createBankTest() {
        Bank bank = new Bank();
        bank.setName("Test");
        bank.setCountryCode("TR");

        Bank savedBank = bankService.createBank(bank);

        Assertions.assertNotNull(savedBank);
        Assertions.assertNotNull(savedBank.getId());
        Assertions.assertEquals("Test", savedBank.getName());
    }
}