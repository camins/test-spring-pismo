package com.test.pismo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.pismo.dtos.TransactionReceiveDTO;
import com.test.pismo.dtos.TransactionReturnDTO;
import com.test.pismo.service.interfaces.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@PostMapping
	public ResponseEntity<TransactionReturnDTO> create(@Valid @RequestBody TransactionReceiveDTO transactionReceive) {
	
		TransactionReturnDTO transactionReturn = transactionService.converterToDTO(
				transactionService.create(transactionReceive));
		return ResponseEntity.status(HttpStatus.CREATED).body(transactionReturn);
	}

}
