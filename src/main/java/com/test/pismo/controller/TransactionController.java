package com.test.pismo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.pismo.domain.Transaction;
import com.test.pismo.dtos.TransactionDTO;
import com.test.pismo.service.interfaces.TransactionService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@ApiOperation(value = "Create the transaction")
	@ApiResponses(value = {
		    @ApiResponse(code = 201, message = "Transaction created with success. Return the transaction created"),
		    @ApiResponse(code = 500, message = "Generated an exception of validation"),
		})
	@PostMapping
	public ResponseEntity<Transaction> create(@Valid @RequestBody TransactionDTO transactionDTO) {
	
		Transaction transaction = transactionService.create(transactionDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
	}

}
