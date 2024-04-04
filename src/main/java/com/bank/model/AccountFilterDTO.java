package com.bank.model;

import com.bank.entities.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountFilterDTO {

    private Long id;
    @NonNull private Long bankId;
    private String bankName;
    private Long accountNumber;
    private Long identificationNumber;
    private Double balance;

    public AccountFilterDTO() {
    }

    public AccountFilterDTO(Long bankId, Long accountNumber) {
        this.bankId = bankId;
        this.accountNumber = accountNumber;
    }
    public AccountFilterDTO(Long id, Long bankId, String bankName, Long accountNumber, Long identificationNumber, Double balance) {
        this.id = id;
        this.bankId = bankId;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.identificationNumber = identificationNumber;
        this.balance = balance;
    }

    public static AccountFilterDTO fromAccount(Account account) {
        return AccountFilterDTO.builder()
                .id(account.getId())
                .bankId(account.getBankId())
                .accountNumber(account.getAccountNumber())
                .identificationNumber(account.getIdentificationNumber())
                .balance(account.getBalance())
                .build();
    }
}
