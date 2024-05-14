package com.errs.management.jwt;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.errs.management.dao.UserDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeUsersDetailsService implements UserDetailsService {
	@Autowired
	UserDAO userDAO;

	private com.errs.management.entities.User userDetail;// specified path as this user is available in spring security
															// also//userdetails fetched from database.

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Inside loadUserByUsername{}", username);
		// TODO Auto-generated method stub
		userDetail = userDAO.findByEmailId(username);
		if (!Objects.isNull(userDetail))
			return new User(userDetail.getEmail(), userDetail.getPassword(), new ArrayList<>());
		else
			throw new UsernameNotFoundException("User not found.");
	}

	// to return complete user details if needed
	public com.errs.management.entities.User getUserDetail() {
//		com.errs.management.entities.User user = userDetail;
//		user.setPassword(null);//user password is not exposed
//		return user;
		return userDetail;// return complete object of userdetails from database
	}

}
