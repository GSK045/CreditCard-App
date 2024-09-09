package com.sm.card_app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sm.card_app.dto.UserDto;
import com.sm.card_app.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

	@Query("""
			SELECT new com.sm.card_app.dto.UserDto(u.id, u.username, u.email,u.roleId, r.roleName)
			         FROM User u
			         JOIN Role r on r.id=u.roleId
			""")
	List<UserDto> getAllUserDetails();

	@Query("""
			SELECT new com.sm.card_app.dto.UserDto(
					u.id,
					u.username,
					u.email,
					u.roleId,
					r.roleName)
			        FROM User u
			        JOIN Role r on r.id=u.roleId
			        where
			        u.email = :email
			        and
			        u.password = :password
			""")
	Optional<UserDto> findByEmailAndPassword(String email, String password);
}
