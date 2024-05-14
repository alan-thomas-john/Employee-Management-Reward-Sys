package com.errs.management.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.errs.management.entities.RecognitionHandler;

@RequestMapping(path = "/RecognitionHandler")
public interface RecognitionHandlerController {

	@PostMapping(path = "/assignedRecognition")
	ResponseEntity<String> assignRecognition(@RequestBody RecognitionHandler recognitionHandler);

	@GetMapping(path = "/getAssignedRecognition/{userId}")
	ResponseEntity<List<RecognitionHandler>> getAssignedRecognitionByUserId(@PathVariable int userId);

	@GetMapping(path = "/getAssignedRecognitionByCount/{userId}")
	ResponseEntity<Integer> getAssignedRecognitionsCountByUserId(@PathVariable int userId);

	@GetMapping(path = "/getAllRecognitions")
	ResponseEntity<List<RecognitionHandler>> getAllRecognitions();
}
