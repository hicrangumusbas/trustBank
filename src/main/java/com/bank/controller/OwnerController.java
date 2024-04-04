package com.bank.controller;

import com.bank.entities.AccountOwner;
import com.bank.service.IAccountOwnerService;
import com.bank.service.impl.AccountOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/ownerService")
public class OwnerController {

    @Autowired
    private IAccountOwnerService accountOwnerService;

    @GetMapping("/account-owner")
    public ResponseEntity<?> getOwnerAccount(@RequestParam Long identificationNumber) {
        if (Objects.isNull(identificationNumber)) {
            String errorMessage = "Identification Number must not be null.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        AccountOwner account = accountOwnerService.getAccountOwner(identificationNumber);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping("/account-owners")
    public ResponseEntity<List<AccountOwner>> getAllAccountOwners() {
        List<AccountOwner> accounts = accountOwnerService.getAllAccountOwners();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PostMapping("/create-account-owner")
    public ResponseEntity<?> createAccountOwner(@RequestBody AccountOwner owner) {
        if (Objects.isNull(owner) || Objects.isNull(owner.getIdentificationNumber()) ||
                Objects.isNull(owner.getFirstName()) || Objects.isNull(owner.getLastName()) ||
                Objects.isNull(owner.getContactNumber())) {

            String errorMessage = "Identification Number, First Name, Last Name, and Contact Number must not be null.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        AccountOwner createdAccount = accountOwnerService.createAccountOwner(owner);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }
}
