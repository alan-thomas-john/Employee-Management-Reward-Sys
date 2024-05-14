package com.errs.management.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.errs.management.constants.ErrsConstants;
import com.errs.management.dao.RecognitionHandlerDAO;
import com.errs.management.entities.RecognitionHandler;
import com.errs.management.entities.Recognitions;
import com.errs.management.entities.User;
import com.errs.management.jwt.JwtFilter;
import com.errs.management.service.RecognitionHandlerService;
import com.errs.management.service.RecognitionService;
import com.errs.management.service.UserService;
import com.errs.management.utils.ErrsUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RecognitionHandlerServiceImpl implements RecognitionHandlerService {

	@Autowired
	private RecognitionHandlerDAO assignedRecognitionHandler;
	@Autowired
	private UserService userService;
	@Autowired
	private RecognitionService recognitionService;
	@Autowired
	private JwtFilter jwtFilter;

	@Override
	public ResponseEntity<String> assignRecognition(RecognitionHandler assignedRecognitions) {
		try {
			if (jwtFilter.isAdmin()) {
				ResponseEntity<User> userResponse = userService.getUserById(assignedRecognitions.getUser().getUserId());
				ResponseEntity<Recognitions> recognitionResponse = recognitionService
						.getRecognitionById(assignedRecognitions.getRecognition().getRecognitionId());

				// Check if the response bodies are not null
				if (userResponse.getBody() != null && recognitionResponse.getBody() != null) {
					User user = userResponse.getBody();
					Recognitions recognition = recognitionResponse.getBody();

					assignedRecognitions.setPoints(recognition.getPoints());
					assignedRecognitions.setRecognition(recognition); // Set the association

					int points;
					int gainedPoints;

					// Check if both points and gained points are 0
					if (recognition.getPoints() == 0) {
						points = 0;
						gainedPoints = 0;
					} else {
						// Calculate the new points as the sum of user's current points and recognition
						// points
						points = user.getPoints() + recognition.getPoints();
						gainedPoints = user.getReceivedPoints() + recognition.getPoints();
					}

					// Update user's points and received points
					userService.updateUser(user, points);
					userService.updatePoint(user, gainedPoints);

					// Save the assigned recognition (assuming cascading is properly configured)
					assignedRecognitionHandler.save(assignedRecognitions);

					return ResponseEntity.ok("Recognition assigned successfully");
				} else {
					// Handle the case when either user or recognition is null
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or recognition not found");
				}
			} else {
				return ErrsUtils.getResponseEntity(ErrsConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error assigning recognition: " + ex.getMessage());
		}
	}

	@Override
	public ResponseEntity<List<RecognitionHandler>> getAssignedRecognitionsByUserId(int userId) {
		// TODO Auto-generated method stub
		try {
			List<RecognitionHandler> recognitionHandlerList = assignedRecognitionHandler.findByUserIdNative(userId);

			return ResponseEntity.ok(recognitionHandlerList);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
//	public List<AssignedRecognitions>  getAssignedRecognitionsByUserId(int userId) {
//		return assignedRecognitionRepository.findByUserIdNative(userId);
//	}

	@Override
	public ResponseEntity<Integer> getAssignedRecognitionsCountByUserId(int userId) {
		// TODO Auto-generated method stub
		try {
			if (jwtFilter.isAdmin()) {
				int count = assignedRecognitionHandler.countByUserIdNative(userId);
				return ResponseEntity.ok(count);
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(0);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0);
		}
	}

	@Override
	public ResponseEntity<List<RecognitionHandler>> getAllRecognitions() {
		try {
			if (jwtFilter.isAdmin()) {
			List<RecognitionHandler> allRecognitions = assignedRecognitionHandler.findAll();
			return ResponseEntity.ok(allRecognitions);
			}else {
				// Return a built ResponseEntity without a body for unauthorized access
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		} catch (Exception ex) {
			log.error("Failed to retrieve recognitions", ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

//	@Override
//	public int getAssignedRecognitionsCountByUserId(int userId) {
//		
//		return assignedRecognitionRepository.countByUserIdNative(userId);
//	}

}
