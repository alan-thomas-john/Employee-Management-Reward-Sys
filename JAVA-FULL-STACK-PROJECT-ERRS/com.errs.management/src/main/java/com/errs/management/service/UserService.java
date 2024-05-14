package com.errs.management.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.errs.management.dto.UserDTO;
import com.errs.management.entities.User;

public interface UserService {
	ResponseEntity<String> signUp(Map<String, String> requestMap);

	ResponseEntity<String> login(Map<String, String> requestMap);

	ResponseEntity<List<UserDTO>> getAllUser();

	ResponseEntity<String> update(Map<String, String> requestMap);

	ResponseEntity<String> checkToken();

	ResponseEntity<User> getUserById(int userId);

	ResponseEntity<String> updateUser(User user, int points);

	ResponseEntity<String> updatePoint(User user, int points);

	ResponseEntity<List<User>> getWinningUser();

}
