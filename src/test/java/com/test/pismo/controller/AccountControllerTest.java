package com.test.pismo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityNotFoundException;

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
import com.test.pismo.dtos.AccountDTO;
import com.test.pismo.service.interfaces.AccountService;

@DisplayName("AccountControllerTest")
public class AccountControllerTest extends SpringTestGeneral {

	@MockBean
	private AccountService accountService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void whenCreatedValidAccount_thenSucess() throws Exception {
		
		AccountDTO aDTO = createAccountReceiveDTO();
		
		Account account = createAccount();
		
		when(accountService.create(aDTO))
			.thenReturn(account);
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/accounts")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(aDTO))
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
		
		ArgumentCaptor<AccountDTO> argument = ArgumentCaptor.forClass(AccountDTO.class);
		verify(accountService, times(1)).create(argument.capture());
		assertEquals(aDTO.getDocumentNumber(), argument.getValue().getDocumentNumber());
		assertEquals(aDTO.getAvailableCreditLimit(), argument.getValue().getAvailableCreditLimit());
			
	}
	
	@Test
	public void whenCreatedInvalidAccount_thenError() throws Exception {
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/accounts")
					.contentType(MediaType.APPLICATION_JSON)
					.content("")
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
		
	}
	
	@Test
	public void whenSearchInvalidAccount_thenError() throws Exception {
		when(accountService.findAccountById(55L))
			.thenThrow(EntityNotFoundException.class);

		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/accounts/55L")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void whenSearchValidAccount_thenSucess() throws Exception {
		
		Account account = createAccount();
		
		when(accountService.findAccountById(1L))
			.thenReturn(account);
		
		this.mockMvc
		.perform(MockMvcRequestBuilders.get("/accounts/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
		
	}
	
	
	private AccountDTO createAccountReceiveDTO() {
		return AccountDTO.builder()
				.documentNumber("12345678900")
				.availableCreditLimit(100.00)
				.build();
	}
	
	private Account createAccount() {
		return Account.builder()
				.id(1L)
				.documentNumber("12345678900")
				.availableCreditLimit(100.00)
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
