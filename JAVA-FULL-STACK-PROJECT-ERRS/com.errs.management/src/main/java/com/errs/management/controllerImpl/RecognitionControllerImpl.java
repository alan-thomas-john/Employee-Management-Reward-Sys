package com.errs.management.controllerImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.errs.management.controller.RecognitionController;
import com.errs.management.entities.Recognitions;
import com.errs.management.service.RecognitionService;
import com.errs.management.utils.ErrsUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RecognitionControllerImpl implements RecognitionController {

	@Autowired
	private RecognitionService recognitionService;

	@Override
	public ResponseEntity<String> addRecognition(Recognitions recognition) {
		try {
			return recognitionService.addRecognition(recognition);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ErrsUtils.getResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateRecognition(int recognitionId, Recognitions recognition) {
		try {
			return recognitionService.updateRecognition(recognitionId, recognition);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ErrsUtils.getResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Recognitions>> getRecognitions() {
		try {
			return recognitionService.getRecognitions();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Recognitions> getRecognitionsById(int recognitionId) {
		try {
			return recognitionService.getRecognitionById(recognitionId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new Recognitions(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> deleteRecognition(int recognitionId) {
		// Calling the service method to delete the recognition by ID
		try {
			recognitionService.deleteRecognition(recognitionId);
			return ErrsUtils.getResponseEntity("Recognition deleted successfully", HttpStatus.OK);
		} catch (Exception ex) {
			// Logging the exception with detailed information
			log.error("Error deleting recognition: ", ex);

			String errorMessage = "Failed to delete recognition. Error: " + ex.getMessage();
			return ErrsUtils.getResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
