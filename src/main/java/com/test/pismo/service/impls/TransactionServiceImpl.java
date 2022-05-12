package com.test.pismo.service.impls;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.pismo.domain.Account;
import com.test.pismo.domain.Transaction;
import com.test.pismo.dtos.TransactionReceiveDTO;
import com.test.pismo.dtos.TransactionReturnDTO;
import com.test.pismo.enums.OperationType;
import com.test.pismo.exceptions.AmountException;
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
	public Transaction create(TransactionReceiveDTO transactionReceive) {
		
		if(transactionReceive.getAccountId() == null) {
			throw new NullPointerException("Account is required");
		}
		
		Transaction transaction = converterToTransaction(transactionReceive);
		
		if(transaction.getAmount() == null || transactionReceive.getAmount() == 0) {
			throw new AmountException("The value of amount is required and can't be equals to 0");
		}
		
		return transactionRepository.save(transaction);
	}

	@Override
	public TransactionReturnDTO converterToDTO(Transaction transaction) {
		
		return TransactionReturnDTO.builder()
				.id(transaction.getId())
				.account(transaction.getAccount().getId())
				.operationType(transaction.getOperationType().getId())
				.amount(transaction.getAmount())
				.eventDate(transaction.getEventDate())
				.build();
	}
	
	private Transaction converterToTransaction(TransactionReceiveDTO transactionReceive) {
		
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
