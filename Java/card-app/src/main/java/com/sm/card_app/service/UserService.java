package com.sm.card_app.service;

import java.util.List;

import com.sm.card_app.bean.PageBean;
import com.sm.card_app.bean.UserBean;
import com.sm.card_app.dto.LoginDto;
import com.sm.card_app.dto.UserDto;

public interface UserService {

	UserBean save(UserBean user);

	UserBean update(UserBean userBean);

	UserBean getById(int id);

	public PageBean<List<UserBean>> getAll(int page, int size);

	UserDto loginUser(LoginDto loginDto);

	public List<UserDto> getAllUserDetails();
}
