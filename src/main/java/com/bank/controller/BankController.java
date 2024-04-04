package com.bank.controller;

import com.bank.entities.Bank;
import com.bank.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bank")
public class BankController {

    @Autowired
    private BankService bankService;


    @GetMapping("/banks")
    public ResponseEntity<List<Bank>> getBanks() {
        List<Bank> banks = bankService.getAllBanks();
        return new ResponseEntity<>(banks, HttpStatus.OK);
    }

    @PostMapping("/create-bank")
    public ResponseEntity<Bank> createBank(@RequestBody Bank bank) {
        Bank createBank = bankService.createBank(bank);
        return new ResponseEntity<>(createBank, HttpStatus.CREATED);
    }
}
