package com.errs.management.entities;



import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Data;

@NamedQuery(name = "Product.getAllProduct", query = "select new com.errs.management.dto.ProductDTO(p.id,p.name,p.description,p.points,p.status,p.category.id,p.category.name,p.manufacturer,p.imageThumbnailUrl,p.redemptionConditions,p.termsAndConditions,p.quantityAvailable) from Product p")

@NamedQuery(name = "Product.updateProductStatus", query = "update Product p set p.status=:status where p.id=:id")

@NamedQuery(name = "Product.getProductByCategory", query = "select new com.errs.management.dto.ProductDTO(p.id,p.name) from Product p where p.category.id=:id and p.status='true'")

@NamedQuery(name = "Product.getProductById", query = "select new com.errs.management.dto.ProductDTO(p.id,p.name,p.description,p.points,p.manufacturer,p.redemptionConditions,p.imageThumbnailUrl,p.quantityAvailable,p.termsAndConditions) from Product p where p.id=:id")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "product")
public class Product{


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_fk", nullable = false) // field should not be null
	@JsonIgnore
	private Category category;// one category has many products

	@Column(name = "description")
	private String description;

	@Column(name = "points")
	private Integer points;

	@Column(name = "manufacturer")
	private String manufacturer;

	@Column(name = "quantityAvailable")
	private Integer quantityAvailable;

	@Column(name = "status")
	private String status;

	@Column(name = "imageThumbnailUrl")
	private String imageThumbnailUrl;

	@Column(name = "redemptionConditions")
	private String redemptionConditions;

	@Column(name = "termsAndConditions")
	private String termsAndConditions;

	@Column(name = "additionalMetadata")
	private String additionalMetadata;

}
