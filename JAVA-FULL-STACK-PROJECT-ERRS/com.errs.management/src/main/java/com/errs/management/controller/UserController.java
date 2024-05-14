package com.errs.management.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.errs.management.dto.UserDTO;
import com.errs.management.entities.User;

@RequestMapping(path = "/user")
public interface UserController {

	@PostMapping(path = "/signup")
	public ResponseEntity<String> signUp(@RequestBody(required = true) Map<String, String> requestMap);

	// whenever user login validation is done and returns token in response
	@PostMapping(path = "/login")
	public ResponseEntity<String> login(@RequestBody(required = true) Map<String, String> requestMap);

	@GetMapping(path = "/get")
	public ResponseEntity<List<UserDTO>> getAllUser();

	@PostMapping(path = "/update")
	public ResponseEntity<String> update(@RequestBody(required = true) Map<String, String> requestMap);

	@GetMapping(path = "/winning-user")
	public ResponseEntity<List<User>> getWinningUser();

	@GetMapping(path = "/checkToken")
	ResponseEntity<String> checkToken();

}
