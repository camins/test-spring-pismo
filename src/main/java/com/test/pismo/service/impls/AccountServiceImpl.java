package com.test.pismo.service.impls;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.test.pismo.domain.Account;
import com.test.pismo.dtos.AccountReturnDTO;
import com.test.pismo.exceptions.EntityNotFoundException;
import com.test.pismo.repository.AccountRepository;
import com.test.pismo.service.interfaces.AccountService;

@Service
public class AccountServiceImpl implements AccountService{

	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	public Account create(Account account) {
		
		if(accountRepository.findAccountByDocumentNumber(
				account.getDocumentNumber()).isPresent()) {
			throw new DataIntegrityViolationException("Document Number already exists");
		}
		
		return accountRepository.save(account);
	}

	@Override
	public Account findAccountById(Long accountId) {
		
		Optional<Account> account = accountRepository.findById(accountId);
		
		if (account.isEmpty()) {
			throw new EntityNotFoundException("Not Found Account with id = "+ accountId);
		}
		
		return account.get();
	}
	
	@Override
	public AccountReturnDTO converter(Account account) {
		return AccountReturnDTO.builder()
				.id(account.getId())
				.documentNumber(account.getDocumentNumber())
				.build();
	}

	
}
