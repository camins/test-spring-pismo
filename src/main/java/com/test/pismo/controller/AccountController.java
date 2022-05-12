package com.test.pismo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.pismo.domain.Account;
import com.test.pismo.dtos.AccountReturnDTO;
import com.test.pismo.service.interfaces.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@PostMapping
	public ResponseEntity<AccountReturnDTO> create(@Valid @RequestBody Account account) {
		
		AccountReturnDTO aDTO = accountService.converter(accountService.create(account)); 
		return ResponseEntity.status(HttpStatus.CREATED).body(aDTO);
	}
	
	@GetMapping(value = "/{accountId}")
	public ResponseEntity<AccountReturnDTO> find(@PathVariable Long accountId) {
		
		AccountReturnDTO aDTO = accountService.converter(
				accountService.findAccountById(accountId)); 
		return ResponseEntity.status(HttpStatus.CREATED).body(aDTO);
	}
}
