package com.bank.enumeration;

public enum TransactionType {
    ALL("Tümü", 0),
    DEPOSIT("Para Yatırma", 1),
    WITHDRAW("Para Çekme", 2);

    private final String type;
    private final Integer value;

    TransactionType(String type, int value) {
        this.type = type;
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
