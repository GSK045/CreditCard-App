package com.sm.card_app.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBean {
	private Integer id;

	private String email;

	private String userName;

	private String password;

	private Integer roleId;

}