package com.errs.management.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private EmployeeUsersDetailsService service;

	Claims claims = null;
	private String userName = null;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// token validation

		if (httpServletRequest.getServletPath().matches("/user/login|/user/forgetPassword|/user/signup")) {
			filterChain.doFilter(httpServletRequest, httpServletResponse);// bypass
		} else {
			String authorizationHeader = httpServletRequest.getHeader("Authorization");
			String token = null;

			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				token = authorizationHeader.substring(7);
				userName = jwtUtil.extractUsername(token);
				claims = jwtUtil.extractAllClaims(token);
			}
			// check for valid values
			if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = service.loadUserByUsername(userName);// extracted from token
				if (jwtUtil.validateToken(token, userDetails)) {
					// validate token with expiration time and username
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);// validation
																												// done
				}

			}
			filterChain.doFilter(httpServletRequest, httpServletResponse);// user pass and access our api and get
																			// response
		}

	}

	// method to check whether the user is admin or is user to get current username
	// with help of token ,extracting role from token and check with below code that
	// is checking for role "admin"
	public boolean isAdmin() {
		return "admin".equalsIgnoreCase((String) claims.get("role"));
	}

	public boolean isUser() {
		return "user".equalsIgnoreCase((String) claims.get("role"));
	}

	public String getCurrentUser() {
		return userName;
	}

}
