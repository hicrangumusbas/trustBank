package com.bank.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "accountOwners")
public class AccountOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long identificationNumber;
    private String firstName;
    private String lastName;
    private Long contactNumber;
    private String mailAddress;

    public AccountOwner() {
    }

    public AccountOwner(Long id, Long identificationNumber, String firstName, String lastName, Long contactNumber, String mailAddress) {
        this.id = id;
        this.identificationNumber = identificationNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.mailAddress = mailAddress;
    }
}
