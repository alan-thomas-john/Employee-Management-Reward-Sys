package com.errs.management.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.errs.management.entities.Recognitions;

public interface RecognitionDAO extends JpaRepository<Recognitions, Integer> {
	
	
	Recognitions getByRecognitionId(int recognitionId);

	List<Recognitions> findAll();
	
	//void deleteByRecognitionId(int recognitionId);
}
