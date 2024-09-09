package com.sm.card_app.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "card_request")
public class CardRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "id")
	private Integer id;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "email")
	private String email;

	@Column(name = "address")
	private String address;

	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

	@Column(name = "income")
	private Double income;

	@Column(name = "id_proof")
	private String idProofNumber;

	@Column(name = "card_approval_status")
	private String cardApprovalStatus;

	@Column(name = "application_date", nullable = false)
	private LocalDate applicationDate;

	@Column(name = "approval_date")
	private LocalDate approvalDate;

	@Column(name = "reason")
	private String reason;

}