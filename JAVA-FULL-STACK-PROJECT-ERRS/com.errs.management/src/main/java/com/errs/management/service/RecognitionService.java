package com.errs.management.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.errs.management.entities.Recognitions;

public interface RecognitionService {

	ResponseEntity<String> addRecognition(Recognitions recognition);

	ResponseEntity<List<Recognitions>> getRecognitions();

	ResponseEntity<Recognitions> getRecognitionById(int recognitionId);

	ResponseEntity<String> updateRecognition(int recognitionId, Recognitions recognition);

	ResponseEntity<String> deleteRecognition(int recognitionId);

}
