package com.errs.management.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.errs.management.constants.ErrsConstants;
import com.errs.management.dao.RecognitionDAO;
import com.errs.management.entities.Recognitions;
import com.errs.management.jwt.JwtFilter;
import com.errs.management.service.RecognitionService;
import com.errs.management.utils.ErrsUtils;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class RecognitionServiceImpl implements RecognitionService {

	@Autowired
	private RecognitionDAO recognitionDAO;

	@Autowired
	private JwtFilter jwtFilter;

	@Override
	public ResponseEntity<String> addRecognition(Recognitions recognition) {
		// TODO Auto-generated method stub
		try {
			if (jwtFilter.isAdmin()) {
				if (validateRecognition(recognition, false)) {
					recognitionDAO.save(recognition);
					return ErrsUtils.getResponseEntity("Recognition Added Successfully", HttpStatus.OK);
				}
				return ErrsUtils.getResponseEntity(ErrsConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
			} else {
				return ErrsUtils.getResponseEntity(ErrsConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception ex) {
			log.error("Error during addRecognition: {}", ex.getMessage(), ex);
			return ErrsUtils.getResponseEntity(ErrsConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private boolean validateRecognition(Recognitions recognition, boolean validateId) {
		if (recognition != null && recognition.getRecognitionName() != null && recognition.getPoints() != null) {
			return !validateId || recognition.getRecognitionId() != null;
		}
		return false;
	}

	@Override
	public ResponseEntity<List<Recognitions>> getRecognitions() {
		try {
			if (jwtFilter.isAdmin()) {
				List<Recognitions> allRecognitions = recognitionDAO.findAll();
				return new ResponseEntity<>(allRecognitions, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception ex) {
			log.error("Error during getRecognitions: {}", ex.getMessage(), ex);
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Recognitions> getRecognitionById(int recognitionId) {
		// TODO Auto-generated method stub
		try {
			if (jwtFilter.isAdmin()) {
				Optional<Recognitions> optionalRecognition = recognitionDAO.findById(recognitionId);

				if (optionalRecognition.isPresent()) {
					Recognitions recognition = optionalRecognition.get();
					return new ResponseEntity<>(recognition, HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			} else {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception ex) {
			log.error("Error during getRecognitionById: {}", ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<String> updateRecognition(int recognitionId, Recognitions recognition) {
		// TODO Auto-generated method stub
		try {
			// Check if recognition object is null
			if (recognition == null) {
				return ErrsUtils.getResponseEntity("Recognition object is null", HttpStatus.BAD_REQUEST);
			}

			// Check if recognitionId is valid
			if (recognitionId <= 0) {
				return ErrsUtils.getResponseEntity("Invalid recognitionId", HttpStatus.BAD_REQUEST);
			}

			// Check if recognitionName is null or empty
			if (recognition.getRecognitionName() == null || recognition.getRecognitionName().isEmpty()) {
				return ErrsUtils.getResponseEntity("Recognition name is required", HttpStatus.BAD_REQUEST);
			}

			// Check if points is null
			if (recognition.getPoints() == null) {
				return ErrsUtils.getResponseEntity("Points are required", HttpStatus.BAD_REQUEST);
			}

			// Proceed with authorization check

			if (jwtFilter.isAdmin()) {
				Optional<Recognitions> optionalRecognition = recognitionDAO.findById(recognitionId);

				if (optionalRecognition.isPresent()) {
					Recognitions existingRecognition = optionalRecognition.get();
					existingRecognition.setRecognitionName(recognition.getRecognitionName());
					existingRecognition.setPoints(recognition.getPoints());

					recognitionDAO.save(existingRecognition);
					return ErrsUtils.getResponseEntity("Recognition Updated Successfully", HttpStatus.OK);
				} else {
					return ErrsUtils.getResponseEntity("Recognition not found", HttpStatus.NOT_FOUND);
				}
			} else {
				return ErrsUtils.getResponseEntity(ErrsConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception ex) {
			log.error("Error during updateRecognition: {}", ex.getMessage(), ex);
			return ErrsUtils.getResponseEntity(ErrsConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<String> deleteRecognition(int recognitionId) {
	    try {
	        if (jwtFilter.isAdmin()) {
	            Optional<Recognitions> optionalRecognition = recognitionDAO.findById(recognitionId);
	            if (optionalRecognition.isPresent()) {
	                recognitionDAO.deleteById(recognitionId);
	                if (recognitionDAO.existsById(recognitionId)) {
	                    return ErrsUtils.getResponseEntity("Failed to delete recognition", HttpStatus.INTERNAL_SERVER_ERROR);
	                } else {
	                    return ErrsUtils.getResponseEntity("Recognition deleted successfully", HttpStatus.OK);
	                }
	            } else {
	                return ErrsUtils.getResponseEntity("Recognition not found", HttpStatus.NOT_FOUND);
	            }
	        } else {
	            return ErrsUtils.getResponseEntity(ErrsConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
	        }
	    } catch (Exception ex) {
	        log.error("Error during deleteRecognition: {}", ex.getMessage(), ex);
	        return ErrsUtils.getResponseEntity(ErrsConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
}
