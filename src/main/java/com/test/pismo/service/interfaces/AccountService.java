package com.test.pismo.service.interfaces;

import com.test.pismo.domain.Account;
import com.test.pismo.dtos.AccountReturnDTO;

public interface AccountService {

	public Account create(Account account);
	
	public Account findAccountById(Long accountId);

	public AccountReturnDTO converter(Account account);
	
}
