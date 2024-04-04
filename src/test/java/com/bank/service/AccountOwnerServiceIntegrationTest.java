package com.bank.service;

import com.bank.entities.AccountOwner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest
public class AccountOwnerServiceIntegrationTest {

    @Autowired
    private AccountOwnerService accountOwnerService;

    @Test
    @DirtiesContext
    void AccountOwner() {
        AccountOwner accountOwner = accountOwnerService.getAccountOwner(10308145519L);
        Assertions.assertNotNull(accountOwner);
    }

    @Test
    @DirtiesContext
    void AllAccountOwner() {
        List<AccountOwner> accountOwners = accountOwnerService.getAllAccountOwners();
        Assertions.assertNotNull(accountOwners);
    }

    @Test
    @DirtiesContext
    void CreateAccount() {
        AccountOwner newOwner = new AccountOwner();
        newOwner.setIdentificationNumber(123453489L);
        newOwner.setFirstName("Ali");
        newOwner.setLastName("Yılmaz");
        newOwner.setContactNumber(987654321L);
        newOwner.setMailAddress("ali.yilmaz@example.com");

        AccountOwner savedOwner = accountOwnerService.createAccountOwner(newOwner);

        Assertions.assertNotNull(savedOwner.getId());

        AccountOwner retrievedOwner = accountOwnerService.getAccountOwner(savedOwner.getIdentificationNumber());
        Assertions.assertEquals(newOwner.getIdentificationNumber(), retrievedOwner.getIdentificationNumber());
        Assertions.assertEquals(newOwner.getFirstName(), retrievedOwner.getFirstName());
        Assertions.assertEquals(newOwner.getLastName(), retrievedOwner.getLastName());
        Assertions.assertEquals(newOwner.getContactNumber(), retrievedOwner.getContactNumber());
        Assertions.assertEquals(newOwner.getMailAddress(), retrievedOwner.getMailAddress());
    }
}
