package com.test.pismo.service.interfaces;

import com.test.pismo.domain.Transaction;
import com.test.pismo.dtos.TransactionReceiveDTO;
import com.test.pismo.dtos.TransactionReturnDTO;

public interface TransactionService {

	public Transaction create(TransactionReceiveDTO transactionReceive);

	public TransactionReturnDTO converterToDTO(Transaction transaction);
}
