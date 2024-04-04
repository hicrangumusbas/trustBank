package com.bank.repository;

import com.bank.entities.Account;
import com.bank.entities.AccountOwner;
import com.bank.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountOwnerRepository  extends JpaRepository<AccountOwner, Long> {

    @Query("SELECT a FROM AccountOwner a WHERE a.identificationNumber = :identificationNumber")
    AccountOwner findByAccountOwner(@Param("identificationNumber") Long accountNumber);
}
