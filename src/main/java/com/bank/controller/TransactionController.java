package com.bank.controller;

import com.bank.entities.Account;
import com.bank.entities.Transaction;
import com.bank.enumeration.AccountFilterType;
import com.bank.enumeration.TransactionType;
import com.bank.service.TransactionService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
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
    public ResponseEntity<Account> depositToAccount(@RequestParam Long bankId, @RequestParam String accountType, @RequestParam Long filterValue, @RequestParam Double amount) throws NotFoundException {
        AccountFilterType type = AccountFilterType.valueOf(accountType);
        Account updatedAccount = transactionService.depositMoney(bankId, type,filterValue, amount);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Account> withdrawToAccount(@RequestParam Long bankId, @RequestParam String accountType, @RequestParam Long filterValue, @RequestParam Double amount) throws NotFoundException {
        AccountFilterType type = AccountFilterType.valueOf(accountType);
        Account updatedAccount = transactionService.withdrawMoney(bankId, type,filterValue, amount);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }
}
