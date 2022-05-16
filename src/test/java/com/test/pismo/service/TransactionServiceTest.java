package com.test.pismo.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.test.pismo.SpringTestGeneral;
import com.test.pismo.domain.Account;
import com.test.pismo.domain.Transaction;
import com.test.pismo.dtos.TransactionDTO;
import com.test.pismo.exceptions.AmountException;
import com.test.pismo.exceptions.BusinessException;
import com.test.pismo.exceptions.EntityNotFoundException;
import com.test.pismo.repository.TransactionRepository;
import com.test.pismo.service.interfaces.AccountService;
import com.test.pismo.service.interfaces.TransactionService;

@DisplayName("TransactionServiceTest")
public class TransactionServiceTest extends SpringTestGeneral {
	
	@MockBean
	private TransactionRepository transactionalRepository;
	
	@MockBean
	private AccountService accountService;
	
	@Autowired
    private TransactionService transactionalService;
	
	@Test
	public void whenCreateTransactional_thenReturnTransaction() {
		
		TransactionDTO transactionDTO = createTransactionDTO();
		
		when(accountService.findAccountById(any()))
			.thenReturn(Mockito.mock(Account.class));
		when(transactionalRepository.save(any()))
		.thenReturn(Mockito.mock(Transaction.class));
		
		transactionalService.create(transactionDTO);
		
		verify(transactionalRepository, Mockito.times(1)).save(ArgumentMatchers.any(Transaction.class));
	}
	
	@Test
	public void whenCreateBlanckAccount_thenError() {
		
		TransactionDTO transactionDTO = TransactionDTO.builder()
									.accountId(null)
									.operationType(1)
									.amount(Double.parseDouble("-50.00"))
									.build();
		
		Assertions.assertThrows(EntityNotFoundException.class, 
				() -> transactionalService.create(transactionDTO), 
				"Account is required");
	}
	
	@Test
	public void whenCreateTransactionWithInvalidAmount_thenError() {
		
		TransactionDTO transactionDTO = TransactionDTO.builder()
				.accountId(1L)
				.operationType(1)
				.amount(null)
				.build();
		
		Exception e = assertThrows(AmountException.class, 
				() -> transactionalService.create(transactionDTO));
		
		String messageReturn = e.getMessage();
		
		assertTrue(messageReturn.contains("The value of amount is required and can't be equals to 0"));
	}
	
	@Test
	public void whenCreateTransactionWithInvalidAmount2_thenError() {
		
		TransactionDTO transactionDTO = TransactionDTO.builder()
				.accountId(1L)
				.operationType(1)
				.amount(Double.parseDouble("0.00"))
				.build();
		
		Exception e = assertThrows(AmountException.class, 
				() -> transactionalService.create(transactionDTO));
		
		String messageReturn = e.getMessage();
		
		assertTrue(messageReturn.contains("The value of amount is required and can't be equals to 0"));
	}
	
	@Test
	public void whenCreateTransactionWithInvalidOperationType_thenError() {
		
		TransactionDTO transactionDTO = TransactionDTO.builder()
				.accountId(1L)
				.operationType(null)
				.amount(Double.parseDouble("50.00"))
				.build();
		
		when(accountService.findAccountById(any()))
			.thenReturn(Mockito.mock(Account.class));
		
		Exception e = assertThrows(BusinessException.class, 
				() -> transactionalService.create(transactionDTO));
		
		String messageReturn = e.getMessage();
		
		assertTrue(messageReturn.contains("Operation Type invalid"));
	}
	
	private TransactionDTO createTransactionDTO() {
		return TransactionDTO.builder()
				.accountId(1L)
				.operationType(1)
				.amount(Double.parseDouble("-50.00"))
				.build();
	}
	
}
