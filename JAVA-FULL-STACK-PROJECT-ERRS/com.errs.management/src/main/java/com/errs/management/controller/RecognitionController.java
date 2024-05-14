package com.errs.management.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.errs.management.entities.Recognitions;

@RequestMapping(path = "/recognition")
public interface RecognitionController {

	@PostMapping(path = "/add")
	ResponseEntity<String> addRecognition(@RequestBody Recognitions recognition);

	@PutMapping(path = "/updateRecognition/{recognitionId}")
	ResponseEntity<String> updateRecognition(@PathVariable int recognitionId, @RequestBody Recognitions recognition);
	
	@DeleteMapping(path = "/deleteRecognition/{recognitionId}")
    ResponseEntity<String> deleteRecognition(@PathVariable int recognitionId);

	@GetMapping(path = "/getRecognition")
	ResponseEntity<List<Recognitions>> getRecognitions();

	@GetMapping(path = "/getRecognitionById/{recognitionId}")
	ResponseEntity<Recognitions> getRecognitionsById(@PathVariable int recognitionId);

}
