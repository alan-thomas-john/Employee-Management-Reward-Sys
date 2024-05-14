package com.errs.management.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.errs.management.dto.ProductDTO;

@RequestMapping(path = "/product")
public interface ProductController {
	// api for adding product
	@PostMapping(path = "/add")
	ResponseEntity<String> addNewProduct(@RequestBody Map<String, String> requestMap);

	@GetMapping(path = "/get")
	ResponseEntity<List<ProductDTO>> getAllProduct();

	// updating product
	@PostMapping(path = "/update")
	ResponseEntity<String> updateProduct(@RequestBody Map<String, String> requestMap);

	// deleting product
	@PostMapping(path = "/delete/{id}")
	ResponseEntity<String> deleteProduct(@PathVariable Integer id);

	// updating status of the product
	@PostMapping(path = "/updateStatus")
	ResponseEntity<String> updateStatus(@RequestBody Map<String, String> requestMap);

	// get by category,if category 5 is given ,all data corresponding to category 5
	// should be returned
	@GetMapping(path = "/getByCategory/{id}")
	ResponseEntity<List<ProductDTO>> getByCategory(@PathVariable Integer id);

	// to get by id
	@GetMapping(path = "/getById/{id}")
	ResponseEntity<ProductDTO> getProductById(@PathVariable Integer id);

}
