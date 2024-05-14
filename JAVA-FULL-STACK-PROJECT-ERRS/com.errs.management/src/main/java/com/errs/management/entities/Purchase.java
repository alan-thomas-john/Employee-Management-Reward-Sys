package com.errs.management.entities;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="purchase_table")
public class Purchase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer purchaseId;
	
	@Column(nullable = true)
	private Integer points;
	
	private Integer quantity;
	
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date date;
	
	@ManyToOne()
	@JoinColumn(name="id")
	private Product product;
	
	@ManyToOne()
	@JoinColumn(name="userId")
	private User user;
}
