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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@ApiOperation(value = "Create the account")
	@ApiResponses(value = {
		    @ApiResponse(code = 201, message = "Account created with success. Return the account created"),
		    @ApiResponse(code = 500, message = "Generated an exception referring to the Document Number"),
		})
	@PostMapping
	public ResponseEntity<AccountDTO> create(@Valid @RequestBody AccountDTO accountDTO) {
		
		AccountDTO aDTO = accountService.converter(accountService.create(accountDTO)); 
		return ResponseEntity.status(HttpStatus.CREATED).body(aDTO);
	}
	
	@ApiOperation(value = "Search the account")
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Account found with success. Return the account"),
		    @ApiResponse(code = 500, message = "Account not found"),
		})
	@GetMapping(value = "/{accountId}")
	public ResponseEntity<AccountDTO> find(@PathVariable Long accountId) {
		
		AccountDTO aDTO = accountService.converter(
				accountService.findAccountById(accountId)); 
		return ResponseEntity.status(HttpStatus.OK).body(aDTO);
	}
}
