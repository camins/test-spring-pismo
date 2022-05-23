package com.test.pismo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.pismo.SpringTestGeneral;
import com.test.pismo.domain.Account;
import com.test.pismo.domain.Transaction;
import com.test.pismo.dtos.TransactionDTO;
import com.test.pismo.enums.OperationType;
import com.test.pismo.service.interfaces.TransactionService;

@DisplayName("TransactionControllerTest")
public class TransactionControllerTest extends SpringTestGeneral {

	@MockBean
	private TransactionService transactionService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void whenCreatedValidTransaction_thenSucess() throws Exception {
		
		TransactionDTO tDTO = createTransactionDTO();
		
		Transaction transaction = createTransaction();
		
		when(transactionService.create(tDTO))
			.thenReturn(transaction);
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/transactions")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(tDTO))
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
		
		ArgumentCaptor<TransactionDTO> argument = ArgumentCaptor.forClass(TransactionDTO.class);
		
		verify(transactionService, times(1)).create(argument.capture());
		assertEquals(tDTO.getAccountId(), argument.getValue().getAccountId());
		assertEquals(tDTO.getAmount(), argument.getValue().getAmount());
		assertEquals(tDTO.getOperationType(), argument.getValue().getOperationType());
			
	}
	
	@Test
	public void whenCreatedInvalidTransaction_thenError() throws Exception {
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/transactions")
					.contentType(MediaType.APPLICATION_JSON)
					.content("")
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
		
	}
	
	private TransactionDTO createTransactionDTO() {
		return TransactionDTO.builder()
				.accountId(1L)
				.operationType(1)
				.amount(Double.parseDouble("-50.00"))
				.build();
	}
	
	private Transaction createTransaction() {
		return Transaction.builder()
				.id(1L)
				.account(new Account(1L, "12345678900", 100.00))
				.operationType(OperationType.COMPRA_A_VISTA.getId())
				.amount(Double.parseDouble("-50.00"))
				.eventDate(LocalDateTime.now())
				.build();
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
}
