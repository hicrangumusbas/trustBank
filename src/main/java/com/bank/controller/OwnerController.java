package com.bank.controller;

import com.bank.entities.AccountOwner;
import com.bank.service.AccountOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owner")
public class OwnerController {

    @Autowired
    private AccountOwnerService accountOwnerService;

    @GetMapping("/account-owner")
    public ResponseEntity<AccountOwner> getOwnerAccount(@RequestParam Long identificationNumber) {
        AccountOwner account = accountOwnerService.getAccountOwner(identificationNumber);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping("/account-owners")
    public ResponseEntity<List<AccountOwner>> getAllAccountOwners() {
        List<AccountOwner> accounts = accountOwnerService.getAllAccountOwners();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PostMapping("/create-account-owner")
    public ResponseEntity<AccountOwner> createAccountOwner(@RequestBody AccountOwner accountOwner) {
        AccountOwner createdAccount = accountOwnerService.createAccountOwner(accountOwner);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }
}
