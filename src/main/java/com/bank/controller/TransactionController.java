package com.bank.controller;

import com.bank.entities.Account;
import com.bank.entities.Transaction;
import com.bank.model.TransactionFilterDTO;
import com.bank.service.ITransactionService;
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
    private ITransactionService transactionService;

    @GetMapping("/transaction-history")
    public ResponseEntity<List<Transaction>> transactionHistory(@RequestParam TransactionFilterDTO filter) {
        List<Transaction> transactionRepositorys = transactionService.getTransactionHistory(filter);
        return new ResponseEntity<>(transactionRepositorys, HttpStatus.OK);
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> depositToAccount(@RequestParam TransactionFilterDTO filter){
        if (Objects.isNull(filter)) {
            String errorMessage = "Bank Id, Account Type, and Filter Value must not be null.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        Account updatedAccount = transactionService.transaction(filter, true);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdrawToAccount(@RequestParam TransactionFilterDTO filter){
        if (Objects.isNull(filter)) {
            String errorMessage = "Bank Id, Account Type, and Filter Value must not be null.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        Account updatedAccount = transactionService.transaction(filter, false);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }
}
