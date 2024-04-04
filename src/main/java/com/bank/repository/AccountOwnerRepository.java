package com.bank.repository;

import com.bank.entities.AccountOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountOwnerRepository  extends JpaRepository<AccountOwner, Long> {

    @Query("SELECT a FROM AccountOwner a WHERE a.identificationNumber = :identificationNumber")
    AccountOwner findByAccountOwner(@Param("identificationNumber") Long accountNumber);
}
