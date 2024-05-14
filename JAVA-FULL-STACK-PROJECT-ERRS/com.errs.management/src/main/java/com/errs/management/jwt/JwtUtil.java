package com.errs.management.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private final String secret = "alansecretkeyYZabcdefghijklmnopqrstuvwxyz0123456789+";

	private Key getSigningKey() {
		byte[] keyBytes = this.secret.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUsername(String token) {
		return extractClaims(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaims(token, Claims::getExpiration);
	}

	public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token).getBody();
	}

	// if token expired
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());// returned date is compared with before date and pass new
															// date
	}

	// to pass values
	public String generateToken(String username, String role) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", role);// key is role and value is role that is passed from user
		return createToken(claims, username);
	}

	// to generate token ,put role ,using jwt default methods in order to create
	// token with help of secret key
	private String createToken(Map<String, Object> claims, String subject) {// subject is username,passed in setsubject
																			// so kept as subject

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))// if you want to expire this
																							// token in 3hrs etc
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

	// validate token,in token we have expiry time and the username ,we match these
	// two
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);// extract username from token check username is valid
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));// if token expired
	}

}
