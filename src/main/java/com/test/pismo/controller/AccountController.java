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

import com.test.pismo.dtos.AccountDTO;
import com.test.pismo.service.interfaces.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@PostMapping
	public ResponseEntity<AccountDTO> create(@Valid @RequestBody AccountDTO accountDTO) {
		
		AccountDTO aDTO = accountService.converter(accountService.create(accountDTO)); 
		return ResponseEntity.status(HttpStatus.CREATED).body(aDTO);
	}
	
	@GetMapping(value = "/{accountId}")
	public ResponseEntity<AccountDTO> find(@PathVariable Long accountId) {
		
		AccountDTO aDTO = accountService.converter(
				accountService.findAccountById(accountId)); 
		return ResponseEntity.status(HttpStatus.OK).body(aDTO);
	}
}
