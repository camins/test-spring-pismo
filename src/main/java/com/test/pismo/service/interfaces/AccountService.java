package com.test.pismo.service.interfaces;

import com.test.pismo.domain.Account;
import com.test.pismo.dtos.AccountDTO;

public interface AccountService {

	public Account create(AccountDTO accountDTO);
	
	public Account findAccountById(Long accountId);

	public AccountDTO converter(Account account);

	public void update(Account account);
	
}
