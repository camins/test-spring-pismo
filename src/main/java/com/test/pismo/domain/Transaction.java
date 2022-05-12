package com.test.pismo.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.test.pismo.enums.OperationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@NotNull(message = "Account is a required field")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="account_id", nullable=false)
	private Account account;

	@NotNull
	@Enumerated(EnumType.ORDINAL)
	@Column(name="operationType_id", nullable=false)
	private OperationType operationType;
	
	@NotNull(message = "Amount is a required field")
	@Column(name="amount", nullable=false)
	private Double amount;
	
	@Column(name="eventDate", nullable=false)
	private LocalDateTime eventDate;
	
	
}
