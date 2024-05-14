package com.errs.management.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.errs.management.dto.UserDTO;
import com.errs.management.entities.User;

public interface UserDAO extends JpaRepository<User, Integer> {
	// if emailid exist in the database or not
	User findByEmailId(@Param("email") String email);// impl in entity

	// return a list of Users and implementing query in User pojo
	List<UserDTO> getAllUser();

	List<String> getAllAdmin();

	// returns integer to userDAO.updateStatus()
	// whenever passing a query to update in userdao,using @Transactional and
	// @modifying
	@Transactional
	@Modifying
	Integer updateStatus(@Param("status") String status, @Param("id") Integer id);

	@Query("SELECT u FROM User u ORDER BY u.receivedPoints DESC")
	List<User> findWinningUsers();

	default List<User> findTopUsersWithTies() {
		List<User> topUsers = findWinningUsers();
		if (topUsers.isEmpty()) {
			return new ArrayList<>();
		}

		// Find the highest points
		int highestPoints = topUsers.get(0).getReceivedPoints();

		// Filtering users with equal points
		List<User> topUsersWithTies = topUsers.stream().filter(user -> user.getReceivedPoints() == highestPoints)
				.collect(Collectors.toList());

		return topUsersWithTies;
	}

	default User findByWinningUser() {
		List<User> topUsersWithTies = findTopUsersWithTies();
		return topUsersWithTies.isEmpty() ? null : topUsersWithTies.get(0);
	}

	default User findByUsername(String username) {
		return findByEmailId(username);
	}
}
