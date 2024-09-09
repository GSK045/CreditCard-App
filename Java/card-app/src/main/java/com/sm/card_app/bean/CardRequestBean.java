package com.sm.card_app.bean;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardRequestBean {

	private Integer id;

	private String userName;

	private String phoneNumber;

	private String email;

	private String address;

	private LocalDate dateOfBirth;

	private Double income;

	private String idProofNumber;

	private String cardApprovalStatus;

	private LocalDate applicationDate;

	private LocalDate approvalDate;

	private String reason;

}