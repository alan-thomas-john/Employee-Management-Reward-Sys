package com.errs.management.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.errs.management.entities.RecognitionHandler;

public interface RecognitionHandlerService {

	ResponseEntity<String> assignRecognition(RecognitionHandler assignedRecognitions);

	ResponseEntity<List<RecognitionHandler>> getAssignedRecognitionsByUserId(int userId);

	ResponseEntity<Integer> getAssignedRecognitionsCountByUserId(int userId);

	ResponseEntity<List<RecognitionHandler>> getAllRecognitions();

}
