package com.test.pismo.service.impls;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.pismo.domain.Account;
import com.test.pismo.domain.Transaction;
import com.test.pismo.dtos.TransactionDTO;
import com.test.pismo.enums.OperationType;
import com.test.pismo.exceptions.AmountException;
import com.test.pismo.exceptions.EntityNotFoundException;
import com.test.pismo.repository.TransactionRepository;
import com.test.pismo.service.interfaces.AccountService;
import com.test.pismo.service.interfaces.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService{

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private AccountService accountService;
	
	@Override
	public Transaction create(TransactionDTO transactionReceive) {
		
		if(transactionReceive.getAccountId() == null) {
			throw new EntityNotFoundException("Account is required");
		}
		
		if(transactionReceive.getAmount() == null || transactionReceive.getAmount() == 0) {
			throw new AmountException("The value of amount is required and can't be equals to 0");
		}
		
		Transaction transaction = converterToTransaction(transactionReceive);
		
		return transactionRepository.save(transaction);
	}
	
	private Transaction converterToTransaction(TransactionDTO transactionReceive) {
		
		Account account = accountService.findAccountById(transactionReceive.getAccountId());
		
		OperationType operationType = OperationType.findOperationById(transactionReceive.getOperationType());
		
		return Transaction.builder()
				.account(account)
				.amount(operationType.getValue(transactionReceive.getAmount()))
				.eventDate(LocalDateTime.now())
				.operationType(operationType)
				.build();
	}
	
}
