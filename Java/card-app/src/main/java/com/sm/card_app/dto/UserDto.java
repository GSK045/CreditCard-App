package com.sm.card_app.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class UserDto {

	private Integer id;

	private String email;

	private String username;

	private Integer roleId;

	private String roleName;

	public UserDto(Integer id, String username, String email, Integer roleId, String roleName) {
		this.id = id;
		this.email = email;
		this.username = username;
		this.roleId = roleId;
		this.roleName = roleName;
	}

}
