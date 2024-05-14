package com.errs.management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
	// declaring variables to store data into variables
	private Integer id;// access only with getters and setters

	private String name;

	private String email;

	private String contactNumber;
	
	private Integer points;

	private String status;

	// designing a constructor based on my queries
	public UserDTO(Integer id, String name, String email, String contactNumber,Integer points, String status) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.contactNumber = contactNumber;
		this.points = points;
		this.status = status;
	}
	// setting di into this dto so for each user there is one dto,now setting user
	// data into a
	// list of userdto
}
