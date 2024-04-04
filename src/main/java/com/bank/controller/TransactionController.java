package com.bank.controller;

import com.bank.entities.Account;
import com.bank.entities.Transaction;
import com.bank.enumeration.AccountFilterType;
import com.bank.enumeration.TransactionType;
import com.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/transactionService")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transaction-history")
    public ResponseEntity<List<Transaction>> transactionHistory(@RequestParam String transactionType, @RequestParam Long filterValue) {
        TransactionType type = TransactionType.valueOf(transactionType);
        List<Transaction> transactionRepositorys = transactionService.getTransactionHistory(type, filterValue);
        return new ResponseEntity<>(transactionRepositorys, HttpStatus.OK);
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> depositToAccount(@RequestParam Long bankId, @RequestParam String accountType, @RequestParam Long filterValue, @RequestParam Double amount){
        if (Objects.isNull(bankId) || Objects.isNull(accountType) || Objects.isNull(filterValue)) {
            String errorMessage = "Bank Id, Account Type, and Filter Value must not be null.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        AccountFilterType type = AccountFilterType.valueOf(accountType);
        Account updatedAccount = transactionService.depositMoney(bankId, type,filterValue, amount);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdrawToAccount(@RequestParam Long bankId, @RequestParam String accountType, @RequestParam Long filterValue, @RequestParam Double amount){
        if (Objects.isNull(bankId) || Objects.isNull(accountType) || Objects.isNull(filterValue)) {
            String errorMessage = "Bank Id, Account Type, and Filter Value must not be null.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        AccountFilterType type = AccountFilterType.valueOf(accountType);
        Account updatedAccount = transactionService.withdrawMoney(bankId, type,filterValue, amount);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }
}
