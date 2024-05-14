package com.errs.management.dto;

import lombok.Data;

@Data
public class ProductDTO {
//specifying columns and keys which returns value
	Integer id;

	String name;

	String description;

	Integer points;

	String status;

	Integer categoryId;

	String categoryName;

	String manufacturer;

	String imageThumbnailUrl;

	String redemptionConditions;

	String termsAndConditions;

	Integer quantityAvailable;

	// constructor in order to select values
	public ProductDTO() {

	}

	// constructor for query
	public ProductDTO(Integer id, String name, String description, Integer points, String status, Integer categoryId,
			String categoryName, String manufacturer, String imageThumbnailUrl, String redemptionConditions,
			String termsAndConditions, Integer quantityAvailable) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.points = points;
		this.status = status;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.manufacturer = manufacturer;
		this.imageThumbnailUrl = imageThumbnailUrl;
		this.redemptionConditions = redemptionConditions;
		this.termsAndConditions = termsAndConditions;
		this.quantityAvailable = quantityAvailable;
	}

	// constructor for third named query
	public ProductDTO(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	// constructor for fourth named query
	 public ProductDTO(Integer id, String name, String description, Integer points, String manufacturer, String redemptionConditions, String imageThumbnailUrl, Integer quantityAvailable, String termsAndConditions) {
	        // initializing fields with provided values
	        this.id = id;
	        this.name = name;
	        this.description = description;
			this.points = points;
	        this.manufacturer = manufacturer;
	        this.redemptionConditions = redemptionConditions;
	        this.imageThumbnailUrl = imageThumbnailUrl;
	        this.quantityAvailable = quantityAvailable;
	        this.termsAndConditions = termsAndConditions;
	    }
}
