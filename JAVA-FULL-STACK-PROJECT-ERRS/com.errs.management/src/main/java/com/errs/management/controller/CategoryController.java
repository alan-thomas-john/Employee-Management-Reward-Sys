package com.errs.management.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.errs.management.entities.Category;

@RequestMapping(path = "/category")
public interface CategoryController {

	@PostMapping(path = "/add")
	ResponseEntity<String> addNewCategory(@RequestBody(required = true) Map<String, String> requestMap);

	// api to get all categories if needed so requestparam is false
	@GetMapping(path = "/get")
	ResponseEntity<List<Category>> getAllCategory(@RequestParam(required = false) String filterValue);

	// updateCategory
	@PostMapping(path = "/update")
	ResponseEntity<String> updatedCategory(@RequestBody(required = true) Map<String, String> requestMap);
}
