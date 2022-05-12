package com.test.pismo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.pismo.domain.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	public Optional<Account> findAccountByDocumentNumber(String documentNumber);
}
