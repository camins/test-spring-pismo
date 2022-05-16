package com.test.pismo.service.interfaces;

import com.test.pismo.domain.Transaction;
import com.test.pismo.dtos.TransactionDTO;

public interface TransactionService {

	public Transaction create(TransactionDTO transactionReceive);

}
