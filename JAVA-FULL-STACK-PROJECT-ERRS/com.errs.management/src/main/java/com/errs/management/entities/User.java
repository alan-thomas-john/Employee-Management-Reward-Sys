package com.errs.management.entities;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@NamedQuery(name = "User.findByEmailId", query = "select u from User u where u.email=:email")

@NamedQuery(name = "User.getAllUser", query = "select new com.errs.management.dto.UserDTO(u.id,u.name,u.email,u.contactNumber,u.points,u.status) from User u where u.role in ('user')")

@NamedQuery(name = "User.updateStatus", query = "update User u set u.status=:status where u.id=:id")

@NamedQuery(name = "User.getAllAdmin", query = "select u.email from User u where u.role='admin'")

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "app_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;

	@Column(name = "name")
	private String name;

	@Column(name = "contactNumber")
	private String contactNumber;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "status")
	private String status;

	@Column(name = "points", nullable = false, columnDefinition = "int default 0")
	private Integer points;

	@Column(name = "receivedPoints", nullable = false, columnDefinition = "int default 0")
	private Integer receivedPoints;

	@Column(name = "role")
	private String role;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPoints() {
		return points != null ? points : 0;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getReceivedPoints() {
		return receivedPoints != null ? receivedPoints : 0;
	}

	public void setReceivedPoints(Integer receivedPoints) {
		this.receivedPoints = receivedPoints;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
