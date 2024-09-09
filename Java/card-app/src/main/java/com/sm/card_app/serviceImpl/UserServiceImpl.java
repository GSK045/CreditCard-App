package com.sm.card_app.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sm.card_app.bean.PageBean;
import com.sm.card_app.bean.UserBean;
import com.sm.card_app.dto.LoginDto;
import com.sm.card_app.dto.UserDto;
import com.sm.card_app.entity.User;
import com.sm.card_app.repository.UserRepository;
import com.sm.card_app.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public UserBean save(UserBean userBean) {
		log.info("Saving user {}", userBean);
		User user = beanToEntity(userBean);
		User savedUser = userRepository.save(user);
		log.info("Saved user successfully {}", savedUser);
		userBean = entityToBean(savedUser);
		return userBean;
	}

	@Override
	public PageBean<List<UserBean>> getAll(int page, int size) {
		try {
			log.info("Retrieving all users");
			Page<User> creditCardRequests = userRepository.findAll(PageRequest.of(page, size));
			List<UserBean> creditRequest = creditCardRequests.getContent().stream().map(menu -> entityToBean(menu))
					.collect(Collectors.toList());
			PageBean<List<UserBean>> pageDto = PageBean.<List<UserBean>>builder().pageNo(creditCardRequests.getNumber())
					.pageSize(creditCardRequests.getSize()).last(creditCardRequests.isLast())
					.first(creditCardRequests.isFirst()).totalPages(creditCardRequests.getTotalPages())
					.totalRecords(creditCardRequests.getTotalElements()).records(creditRequest).build();
			return pageDto;
		} catch (Exception exception) {
			log.error("Error occured while fetching all users", exception);
			throw exception;
		}
	}
	
	@Override
	public UserDto loginUser(LoginDto loginDto) {
		log.info("service impl Login user");
		Optional<UserDto> user= userRepository.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
		System.out.println(user.get());
		return user.get();
	}

	@Override
	public UserBean getById(int id) {
		log.info("Retrieving userBean By Id {}", id);
		User userBean = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("No Record Found with given id"));
		return entityToBean(userBean);
	}

	@Override
	public UserBean update(UserBean userBean) {
		UserBean dbUserBean = getById(userBean.getId());
		if (ObjectUtils.isNotEmpty(dbUserBean)) {
			log.info("Update User {}", userBean);
			User user = beanToEntity(userBean);
			return entityToBean(userRepository.save(user));
		} else {
			log.info("user with id {} not found", userBean);
			throw new IllegalArgumentException("user not found for updating");
		}
	}

	public User beanToEntity(UserBean userBean) {
		return objectMapper.convertValue(userBean, User.class);
	}

	public UserBean entityToBean(User user) {
		return objectMapper.convertValue(user, UserBean.class);
	}

	public List<UserBean> entityToBean(List<User> list) {
		List<UserBean> beanList = new ArrayList<>();
		for (User user : list) {
			UserBean userBean;
			userBean = objectMapper.convertValue(user, UserBean.class);
			beanList.add(userBean);
		}
		return beanList;
	}

	@Override
	public List<UserDto> getAllUserDetails() {
		log.info("Fetching all userdto");
		List<UserDto> userDto = userRepository.getAllUserDetails();
		log.info("Fetching all user details {]", userDto.size());
		return userDto;
	}

}
