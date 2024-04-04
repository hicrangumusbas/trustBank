package com.bank.repository;

import com.bank.entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

    @Query("SELECT a FROM Bank a WHERE a.name = :bankName")
    Bank findByBank(@Param("bankName") String bankName);
}
