package com.errs.management.entities;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "RecognitionHandler_table")
public class RecognitionHandler {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer assignmentId;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date date;

	@Column(nullable = true)
	private Integer points;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "recognitionId", nullable = false)
	private Recognitions recognition;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	private User user;
}
