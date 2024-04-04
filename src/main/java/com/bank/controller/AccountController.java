package com.bank.controller;

import com.bank.entities.Account;
import com.bank.enumeration.AccountFilterType;
import com.bank.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/accountService")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @GetMapping("/account")
    public ResponseEntity<?> getAccount(@RequestParam Long bankId, @RequestParam String accountType, @RequestParam Long filterValue) {
        if (Objects.isNull(bankId) || Objects.isNull(accountType) || Objects.isNull(filterValue)) {
            String errorMessage = "Bank Id, Account Type, and Filter Value must not be null.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        AccountFilterType type = AccountFilterType.valueOf(accountType);
        Account account = accountService.getAccount(bankId, type, filterValue);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping("/owner-accounts")
    public ResponseEntity<?> getOwnerAccounts(@RequestParam String accountType, @RequestParam Long filterValue) {
        if (Objects.isNull(accountType) || Objects.isNull(filterValue)) {
            String errorMessage = "Account Type, and Filter Value must not be null.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        AccountFilterType type = AccountFilterType.valueOf(accountType);
        List<Account> accounts = accountService.getOwnerAccounts(type, filterValue);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PostMapping("/create-account")
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        if (Objects.isNull(account.getIdentificationNumber()) || Objects.isNull(account.getAccountNumber()) || Objects.isNull(account.getBankId())) {
            String errorMessage = "Identification Number, Account Number, and Bank ID must not be null.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        Account createdAccount = accountService.createAccount(account);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @GetMapping("/balance")
    public ResponseEntity<?> checkBalance(@RequestParam Long accountId) {
        if (Objects.isNull(accountId)) {
            String errorMessage = "Account Id must not be null.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        
        Double balance = accountService.checkBalance(accountId);
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }
}
