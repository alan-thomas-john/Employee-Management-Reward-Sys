package com.errs.management.controllerImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.errs.management.controller.RecognitionHandlerController;
import com.errs.management.entities.RecognitionHandler;
import com.errs.management.service.RecognitionHandlerService;
import com.errs.management.utils.ErrsUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RecognitionHandlerControllerImpl implements RecognitionHandlerController {

	@Autowired
	private RecognitionHandlerService assignedRecognitionService;

	@Override
	public ResponseEntity<String> assignRecognition(RecognitionHandler recognitionHandler) {
		try {
			assignedRecognitionService.assignRecognition(recognitionHandler);
			return ErrsUtils.getResponseEntity("Recognition assigned successfully", HttpStatus.OK);
		} catch (Exception ex) {
			// Logging the exception with detailed information
			log.error("Error assigning recognition: ", ex);

			String errorMessage = "Failed to assign recognition. Error: " + ex.getMessage();
			return ErrsUtils.getResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<List<RecognitionHandler>> getAssignedRecognitionByUserId(int userId) {
		// TODO Auto-generated method stub
		try {
			return assignedRecognitionService.getAssignedRecognitionsByUserId(userId);
		} catch (Exception ex) {

			log.error("Failed to retrieve recognitions by user ID: {}", userId, ex);
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Integer> getAssignedRecognitionsCountByUserId(int userId) {
		// TODO Auto-generated method stub
		try {
			return assignedRecognitionService.getAssignedRecognitionsCountByUserId(userId);
		} catch (Exception ex) {
			log.error("Failed to count recognitions for user ID: {}", userId, ex);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(-1);
	}

	@Override
	public ResponseEntity<List<RecognitionHandler>> getAllRecognitions() {
		try {
			return assignedRecognitionService.getAllRecognitions();
			
		} catch (Exception ex) {
			log.error("Failed to retrieve all recognitions", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
