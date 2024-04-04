package com.bank.enumeration;

public enum AccountFilterType {
    ID("ID"),
    BANK_ID("BANK_ID"),
    ACCOUNT_NUMBER("ACCOUNT_NUMBER"),
    IDENTIFICATION_NUMBER("IDENTIFICATION_NUMBER");

    private final String type;

    AccountFilterType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
