package com.test.pismo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.pismo.domain.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
