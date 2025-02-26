package com.errs.management.controllerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.errs.management.constants.ErrsConstants;
import com.errs.management.controller.CategoryController;
import com.errs.management.entities.Category;
import com.errs.management.service.CategoryService;
import com.errs.management.utils.ErrsUtils;

@RestController
public class CategoryControllerImpl implements CategoryController {

	@Autowired
	CategoryService categoryService;

	@Override
	public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		try {
			return categoryService.addNewCategory(requestMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ErrsUtils.getResponseEntity(ErrsConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
		// TODO Auto-generated method stub
		try {
			return categoryService.getAllCategory(filterValue);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updatedCategory(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		try {
			return categoryService.updateCategory(requestMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ErrsUtils.getResponseEntity(ErrsConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
