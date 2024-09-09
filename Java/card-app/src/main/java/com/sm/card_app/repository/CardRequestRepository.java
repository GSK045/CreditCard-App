package com.sm.card_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sm.card_app.entity.CardRequest;

@Repository
public interface CardRequestRepository
		extends JpaRepository<CardRequest, Integer>, JpaSpecificationExecutor<CardRequest> {

	CardRequest findByEmailOrPhoneNumber(String email, String phoneNumber);

	@Query("SELECT c FROM CardRequest c WHERE c.email LIKE %?1%")
	List<CardRequest> findByEmailContaining(String email);

//	List<CardRequest> findAllByEmail(String email);

}
