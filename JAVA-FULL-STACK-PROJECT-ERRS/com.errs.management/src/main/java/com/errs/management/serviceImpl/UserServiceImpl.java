package com.errs.management.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.errs.management.constants.ErrsConstants;
import com.errs.management.dao.UserDAO;
import com.errs.management.dto.UserDTO;
import com.errs.management.entities.User;
import com.errs.management.jwt.EmployeeUsersDetailsService;
import com.errs.management.jwt.JwtFilter;
import com.errs.management.jwt.JwtUtil;
import com.errs.management.service.UserService;
import com.errs.management.utils.EmailUtils;
import com.errs.management.utils.ErrsUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDAO userDAO;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	EmployeeUsersDetailsService employeeUsersDetailsService;

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtFilter jwtFilter;

	@Autowired
	EmailUtils emailUtils;

	@Override
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {
		log.info("Inside signup {}", requestMap);
		try {
			if (validateSignUpMap(requestMap)) {
				User user = userDAO.findByEmailId(requestMap.get("email"));
				if (Objects.isNull(user)) {
					User newUser = getUserFromMap(requestMap);
					// Set default values for points and receivedPoints
					newUser.setPoints(0);
					newUser.setReceivedPoints(0);

					// Encode the password before saving it to the database
					String encodedPassword = passwordEncoder.encode(newUser.getPassword());
					newUser.setPassword(encodedPassword);
					userDAO.save(newUser); // persist the data into the database

					return ErrsUtils.getResponseEntity("Succesfully registered.", HttpStatus.OK);
				} else {
					return ErrsUtils.getResponseEntity("Email already exists.", HttpStatus.BAD_REQUEST);
				}
			} else {
				return ErrsUtils.getResponseEntity(ErrsConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ErrsUtils.getResponseEntity(ErrsConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private boolean validateSignUpMap(Map<String, String> requestMap) {
		if (requestMap.containsKey("name") && requestMap.containsKey("contactNumber") && requestMap.containsKey("email")
				&& requestMap.containsKey("password")) {
			return true;
		}
		return false;

	}

	private User getUserFromMap(Map<String, String> requestMap) {
		User user = new User();
		user.setName(requestMap.get("name"));
		user.setContactNumber(requestMap.get("contactNumber"));
		user.setEmail(requestMap.get("email"));
		user.setPassword(requestMap.get("password"));
		user.setStatus("false");
		user.setRole("user");
		return user;

	}

	@Override
	public ResponseEntity<String> login(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		log.info("Inside login");
		try {
			Authentication auth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
			if (auth.isAuthenticated()) {
				if (employeeUsersDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")) {
					// admin has approved user or not
					return new ResponseEntity<String>(
							"{\"token\":\""
									+ jwtUtil.generateToken(employeeUsersDetailsService.getUserDetail().getEmail(),
											employeeUsersDetailsService.getUserDetail().getRole())
									+ "\"}",
							HttpStatus.OK);
				} else {
					return new ResponseEntity<String>("{\"message\":\"" + "Wait for admin approval." + "\"}",
							HttpStatus.BAD_REQUEST);
				}
			}
		} catch (Exception ex) {
			log.error("Error during login: {}", ex.getMessage(), ex);
		}
		return new ResponseEntity<String>("{\"message\":\"" + "Bad Credentials." + "\"}", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<List<UserDTO>> getAllUser() {
		// TODO Auto-generated method stub
		// Handling with exception
		try {
			if (jwtFilter.isAdmin()) {
				return new ResponseEntity<>(userDAO.getAllUser(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> update(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		try {
			if (jwtFilter.isAdmin()) {
				// check if that particular user exist in our database or not
				Optional<User> optional = userDAO.findById(Integer.parseInt(requestMap.get("id")));
				if (!optional.isEmpty()) {
					// updating
					userDAO.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));// accepting
																											// values
																											// from
																											// requestmap
					// after updating display message
					sendMailToAllAdmin(requestMap.get("status"), optional.get().getEmail(), userDAO.getAllAdmin());
					return ErrsUtils.getResponseEntity("User Status Updated Successfully", HttpStatus.OK);
				} else {
					return ErrsUtils.getResponseEntity("User id does not exist", HttpStatus.OK);
				}
			} else {
				return ErrsUtils.getResponseEntity(ErrsConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ErrsUtils.getResponseEntity(ErrsConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {
		// TODO Auto-generated method stub
		// remove current user
		allAdmin.remove(jwtFilter.getCurrentUser());// passing current user and no need to send mail twice to same user
		if (status != null && status.equalsIgnoreCase("true")) {
			emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account is Approved",
					"USER:- " + user + "\n is approved by \nADMIN:-" + jwtFilter.getCurrentUser(), allAdmin);
		} else {
			emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account is Disabled",
					"USER:- " + user + "\n is disabled by \nADMIN:-" + jwtFilter.getCurrentUser(), allAdmin);
		}

	}

	@Override
	public ResponseEntity<String> checkToken() {
		// this api is used whenever we move from one page to another if user want to
		// deny to admin page,so to validate user
		// using this api if any invalid token is given return error and if hit with
		// proper valid token,it will return true
		// TODO Auto-generated method stub
		return ErrsUtils.getResponseEntity("true", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<User> getUserById(int userId) {
		// TODO Auto-generated method stub
		try {
			Optional<User> optionalUser = userDAO.findById(userId);
			if (optionalUser.isPresent()) {
				User user = optionalUser.get();
				return new ResponseEntity<>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<String> updateUser(User user, int points) {
		// TODO Auto-generated method stub
		try {
			if (userDAO.existsById(user.getUserId())) {
				user = getUserById(user.getUserId()).getBody(); // Get the user details
				user.setPoints(points); // Update the points
				userDAO.save(user); // Save the updated user

				return ResponseEntity.ok("User updated successfully");
			} else {
				return ResponseEntity.notFound().build(); // User not found
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user");
	}

	@Override
	public ResponseEntity<String> updatePoint(User user, int points) {
		// TODO Auto-generated method stub
		try {
			if (jwtFilter.isAdmin()) {
				if (userDAO.existsById(user.getUserId())) {
					user = getUserById(user.getUserId()).getBody(); // Get the user details
					user.setReceivedPoints(points); // Update the received points
					userDAO.save(user); // Save the updated user
				}
			} else {
				return ErrsUtils.getResponseEntity(ErrsConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating points");
	}

	@Override
	public ResponseEntity<List<User>> getWinningUser() {
		// TODO Auto-generated method stub
		try {
			List<User> winningUsers = userDAO.findTopUsersWithTies();
			if (!winningUsers.isEmpty()) {
				return ResponseEntity.ok(winningUsers);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
