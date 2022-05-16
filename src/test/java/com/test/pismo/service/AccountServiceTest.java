package com.test.pismo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.test.pismo.SpringTestGeneral;
import com.test.pismo.domain.Account;
import com.test.pismo.dtos.AccountDTO;
import com.test.pismo.exceptions.BusinessException;
import com.test.pismo.repository.AccountRepository;
import com.test.pismo.service.interfaces.AccountService;

@DisplayName("AccountServiceTest")
public class AccountServiceTest extends SpringTestGeneral {
	
	@MockBean
	private AccountRepository accountRepository;
	
	@Autowired
    private AccountService accountService;
	
	@Test
	public void whenCreateAccount_thenReturnAccount() {
		
		String documentNumber = "documentNumber-mock";
		when(accountRepository.findAccountByDocumentNumber(documentNumber))
			.thenReturn(Optional.ofNullable(null));
		
		AccountDTO aDTO = createAccountDTO();
		accountService.create(aDTO);
		
		verify(accountRepository, Mockito.times(1)).save(ArgumentMatchers.any(Account.class));
	}
	
	@Test
	public void whenCreateBlanckAccount_thenError() {
		
		AccountDTO aDTO = AccountDTO.builder()
				.documentNumber(null)
				.build();
		
		Assertions.assertThrows(BusinessException.class, 
				() -> accountService.create(aDTO), 
				"Document Number is required");
	}
	
	@Test
	public void whenCreateAccountWithDocumentNumberAlreadyExists_thenError() {
		
		AccountDTO aDTO = createAccountDTO();
		accountService.create(aDTO);
		
		when(accountRepository.findAccountByDocumentNumber(any()))
			.thenReturn(Optional.of(Mockito.mock(Account.class)));
		
		Exception e = assertThrows(BusinessException.class, 
				() -> accountService.create(aDTO));
		
		String messageReturn = e.getMessage();
		
		assertTrue(messageReturn.contains("Document Number already exists"));
	}
	
	@Test
	public void whenFindAccountDoesntExists_thenError() {
		when(accountRepository.findById(any()))
			.thenReturn(Optional.ofNullable(null));
		
		Exception e = assertThrows(EntityNotFoundException.class, 
				() -> accountService.findAccountById(any()));
		
		String messageReturn = e.getMessage();
		
		assertTrue(messageReturn.contains("Not Found Account with id = "));
	}

	@Test
	public void whenFindAccountById_thenReturnAccount() {
		
		Account a = createAccount();
		when(accountRepository.findById(any()))
			.thenReturn(Optional.of(a));
		
		Account ret = accountService.findAccountById(a.getId());

		assertEquals(a.getId(), ret.getId());
		assertEquals(a.getDocumentNumber(), ret.getDocumentNumber());
		//assertNotNull(ret.getTransactions());
	}
	
	private AccountDTO createAccountDTO() {
		return AccountDTO.builder()
				.documentNumber("12345678900")
				.build();
	}
	
	private Account createAccount() {
		return Account.builder()
				.id(1L)
				.documentNumber("12345678900")
				//.transactions(new HashSet<>())
				.build();
	}

}
