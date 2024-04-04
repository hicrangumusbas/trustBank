package com.bank.repository;

import com.bank.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE " +
            "a.bankId = :bankId AND " +
            "((:filterType = 'ID' AND a.id = :filterValue) OR " +
            "(:filterType = 'ACCOUNT_NUMBER' AND a.accountNumber = :filterValue) OR " +
            "(:filterType = 'IDENTIFICATION_NUMBER' AND a.identificationNumber = :filterValue))")
    List<Account> findAccountsBankFilter(@Param("bankId") Long bankId,
                                       @Param("filterType") String filterType,
                                       @Param("filterValue") Long filterValue);

    @Query("SELECT a FROM Account a WHERE " +
            "(:filterType = 'ACCOUNT_NUMBER' AND a.accountNumber = :filterValue) OR " +
            "(:filterType = 'IDENTIFICATION_NUMBER' AND a.identificationNumber = :filterValue)")
    List<Account> findAccountsByFilter(@Param("filterType") String filterType,
                                       @Param("filterValue") Long filterValue);

}
