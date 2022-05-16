package com.test.pismo.service.impls;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.pismo.domain.Account;
import com.test.pismo.dtos.AccountDTO;
import com.test.pismo.exceptions.BusinessException;
import com.test.pismo.exceptions.EntityNotFoundException;
import com.test.pismo.repository.AccountRepository;
import com.test.pismo.service.interfaces.AccountService;

@Service
public class AccountServiceImpl implements AccountService{

	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	public Account create(AccountDTO accountDTO) {
		
		if(accountDTO.getDocumentNumber() == null
				|| accountDTO.getDocumentNumber().trim().isBlank()) {
			throw new BusinessException("Document Number is required");
		}
		
		if(accountRepository.findAccountByDocumentNumber(
				accountDTO.getDocumentNumber()).isPresent()) {
			throw new BusinessException("Document Number already exists");
		}
		
		return accountRepository.save(converterDTOToAccount(accountDTO));
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
	public AccountDTO converter(Account account) {
		return AccountDTO.builder()
				.id(account.getId())
				.documentNumber(account.getDocumentNumber())
				.build();
	}
	
	private Account converterDTOToAccount(AccountDTO accountDTO) {
		return Account.builder()
				.documentNumber(accountDTO.getDocumentNumber())
				.build();
	}

	
}
