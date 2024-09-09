package com.sm.card_app.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sm.card_app.bean.RoleBean;
import com.sm.card_app.entity.Role;
import com.sm.card_app.repository.RoleRepository;
import com.sm.card_app.service.RoleService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public RoleBean save(RoleBean roleBean) {
		log.info("Saving role {}", roleBean);
		Role role = beanToEntity(roleBean);
		Role savedRole = roleRepository.save(role);
		log.info("Saved role successfully {}", savedRole);
		roleBean = entityToBean(savedRole);
		return roleBean;
	}

	@Override
	public RoleBean getById(int id) {
		log.info("Retrieving roleBean By Id {}", id);
		Role roleBean = roleRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("No Record Found with given id"));
		return entityToBean(roleBean);
	}

	

	@Override
	public RoleBean update(RoleBean roleBean) {
		RoleBean dbRoleBean = getById(roleBean.getId());
		if (ObjectUtils.isNotEmpty(dbRoleBean)) {
			log.info("Update Role {}", roleBean);
			Role role = beanToEntity(roleBean);
			return entityToBean(roleRepository.save(role));
		} else {
			log.info("role with id {} not found", roleBean);
			throw new IllegalArgumentException("role not found for updating");
		}
	}

	@Override
	public List<RoleBean> fetchAll() {
		log.info("Fetched all roles successfully");
		List<Role> roles = roleRepository.findAll();
		List<RoleBean> roleBeans = entityToBean(roles);
		log.info("Fetched roles successfully {}", roleBeans.size());
		return roleBeans;
	}

	public Role beanToEntity(RoleBean roleBean) {
		return objectMapper.convertValue(roleBean, Role.class);
	}

	public RoleBean entityToBean(Role role) {
		return objectMapper.convertValue(role, RoleBean.class);
	}

	public List<RoleBean> entityToBean(List<Role> list) {
		List<RoleBean> beanList = new ArrayList<>();
		for (Role role : list) {
			RoleBean roleBean;
			roleBean = objectMapper.convertValue(role, RoleBean.class);
			beanList.add(roleBean);
		}
		return beanList;
	}

}
