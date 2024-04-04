package com.bank.controller;

import com.bank.entities.Bank;
import com.bank.service.IBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/bankService")
public class BankController {

    @Autowired
    private IBankService bankService;


    @GetMapping("/banks")
    public ResponseEntity<List<Bank>> getBanks() {
        List<Bank> banks = bankService.getAllBanks();
        return new ResponseEntity<>(banks, HttpStatus.OK);
    }

    @PostMapping("/create-bank")
    public ResponseEntity<?> createBank(@RequestBody Bank bank) {
        if (Objects.isNull(bank.getName()) || Objects.isNull(bank.getCountryCode())) {
            String errorMessage = "Bank Name, and Country Code must not be null.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        Bank createBank = bankService.createBank(bank);
        return new ResponseEntity<>(createBank, HttpStatus.CREATED);
    }
}
