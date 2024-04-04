package com.bank.model;

import lombok.*;

@Getter
@Setter
@Builder
public class TransactionFilterDTO {
    private Long id;
    @NonNull
    private Long bankId;
    private Long accountNumber;
    private Long transactionDate;
    private Double amount;
    private Integer transactionType;
    private Boolean success;

    public TransactionFilterDTO() {
    }

    public TransactionFilterDTO(Long bankId, Long accountNumber, Double amount, Integer transactionType) {
        this.bankId = bankId;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.transactionType = transactionType;
    }
    public TransactionFilterDTO(Long id, Long bankId, Long accountNumber, Long transactionDate, Double amount, Integer transactionType, Boolean success) {
        this.id = id;
        this.bankId = bankId;
        this.accountNumber = accountNumber;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.transactionType = transactionType;
        this.success = success;
    }

    public boolean isNullFieldExist() {
        return accountNumber == null || amount == null;
    }

    public static AccountFilterDTO fromAccount(TransactionFilterDTO transaction) {
        return AccountFilterDTO.builder()
                .bankId(transaction.getBankId())
                .accountNumber(transaction.getAccountNumber())
                .build();
    }
}
