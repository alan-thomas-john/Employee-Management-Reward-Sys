package com.errs.management.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.errs.management.dto.ProductDTO;

public interface ProductService {

	ResponseEntity<String> addNewProduct(Map<String, String> requestMap);

	ResponseEntity<List<ProductDTO>> getAllProduct();

	ResponseEntity<String> updateProduct(Map<String, String> requestMap);

	ResponseEntity<String> deleteProduct(Integer id);

	ResponseEntity<String> updateStatus(Map<String, String> requestMap);

	ResponseEntity<List<ProductDTO>> getByCategory(Integer id);

	ResponseEntity<ProductDTO> getProductById(Integer id);

	ResponseEntity<String> buyProduct(ProductDTO product);

}
