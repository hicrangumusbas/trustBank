package com.bank.repository;

import com.bank.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    @Query("SELECT a FROM Transaction a WHERE " +
            "(:filterType != 0 AND a.transactionType = :filterType) " +
            "OR (:filterType = 0) " +
            "AND a.accountNumber = :accountNumber")
    List<Transaction> findByAccountNumber(@Param("filterType") int type,
                                          @Param("accountNumber") Long accountNumber);
}
