package com.sm.card_app.service;

import java.util.List;

import com.sm.card_app.bean.RoleBean;

public interface RoleService {

	RoleBean save(RoleBean role);

	RoleBean update(RoleBean roleBean);

	RoleBean getById(int id);

	List<RoleBean> fetchAll();

}
