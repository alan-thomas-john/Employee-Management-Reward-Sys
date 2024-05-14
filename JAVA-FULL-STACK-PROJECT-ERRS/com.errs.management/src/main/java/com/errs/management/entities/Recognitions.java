package com.errs.management.entities;

import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "recognitions_table")
public class Recognitions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer recognitionId;

	private String recognitionName;
	private Integer points;

	@OneToMany(mappedBy = "recognition", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<RecognitionHandler> handlers;

}
