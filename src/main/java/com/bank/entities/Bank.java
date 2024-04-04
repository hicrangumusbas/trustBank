package com.bank.entities;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "banks")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String countryCode;

    public Bank() {
    }

    public Bank(Long id, String name, String countryCode) {
        this.id = id;
        this.name = name;
        this.countryCode = countryCode;
    }
}
