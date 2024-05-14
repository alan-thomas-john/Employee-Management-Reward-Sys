package com.errs.management.controllerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.errs.management.constants.ErrsConstants;
import com.errs.management.controller.ProductController;
import com.errs.management.dto.ProductDTO;
import com.errs.management.service.ProductService;
import com.errs.management.utils.ErrsUtils;

@RestController
public class ProductControllerImpl implements ProductController {

	@Autowired
	ProductService productService;

	@Override
	public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		try {
			return productService.addNewProduct(requestMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ErrsUtils.getResponseEntity(ErrsConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<ProductDTO>> getAllProduct() {
		// TODO Auto-generated method stub
		try {
			// return productservice
			return productService.getAllProduct();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		try {
			return productService.updateProduct(requestMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ErrsUtils.getResponseEntity(ErrsConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> deleteProduct(Integer id) {
		// TODO Auto-generated method stub
		try {
			return productService.deleteProduct(id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ErrsUtils.getResponseEntity(ErrsConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		try {
			return productService.updateStatus(requestMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ErrsUtils.getResponseEntity(ErrsConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<ProductDTO>> getByCategory(Integer id) {
		// TODO Auto-generated method stub
		try {
			return productService.getByCategory(id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<ProductDTO> getProductById(Integer id) {
		// TODO Auto-generated method stub
		try {
			return productService.getProductById(id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ProductDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
