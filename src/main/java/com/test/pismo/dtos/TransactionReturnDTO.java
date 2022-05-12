package com.test.pismo.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionReturnDTO {

	private Long id;
	
	private Long account;

	private Integer operationType;
	
	private Double amount;
	
	private LocalDateTime eventDate;
}
