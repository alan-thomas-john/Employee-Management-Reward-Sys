package com.errs.management.controllerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.errs.management.constants.ErrsConstants;
import com.errs.management.controller.UserController;
import com.errs.management.dto.UserDTO;
import com.errs.management.entities.User;
import com.errs.management.service.UserService;
import com.errs.management.utils.ErrsUtils;

@RestController
public class UserControllerImpl implements UserController {
	@Autowired
	UserService userService;

	@Override
	public ResponseEntity<String> signUp(@RequestBody Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		try {
			return userService.signUp(requestMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ErrsUtils.getResponseEntity(ErrsConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> login(@RequestBody Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		try {
			return userService.login(requestMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ErrsUtils.getResponseEntity(ErrsConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<UserDTO>> getAllUser() {
		// TODO Auto-generated method stub
		// handling exceptions
		try {
			return userService.getAllUser();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<List<UserDTO>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> update(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		try {
			return userService.update(requestMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ErrsUtils.getResponseEntity(ErrsConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> checkToken() {
		// TODO Auto-generated method stub
		try {
			return userService.checkToken();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ErrsUtils.getResponseEntity(ErrsConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<User>> getWinningUser() {
	    try {
	        return userService.getWinningUser();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

}
