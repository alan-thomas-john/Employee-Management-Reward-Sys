package com.errs.management.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.errs.management.constants.ErrsConstants;
import com.errs.management.dao.CategoryDAO;
import com.errs.management.entities.Category;
import com.errs.management.jwt.JwtFilter;
import com.errs.management.service.CategoryService;
import com.errs.management.utils.ErrsUtils;
import com.google.common.base.Strings;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryDAO categoryDAO;

	@Autowired
	JwtFilter jwtFilter;

	@Override
	public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		try {
			// only admin can add the category
			// check if it is admin or user
			if (jwtFilter.isAdmin()) {
				if (validateCategoryMap(requestMap, false)) {
					categoryDAO.save(getCategoryFromMap(requestMap, false));// saves data into the database
					// also need response
					return ErrsUtils.getResponseEntity("Category Added Successfully", HttpStatus.OK);
				}
			} else {
				return ErrsUtils.getResponseEntity(ErrsConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ErrsUtils.getResponseEntity(ErrsConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {// if passed false gos to
																						// name and not check id
		// TODO Auto-generated method stub
		if (requestMap.containsKey("name")) {
			if (requestMap.containsKey("id") && validateId) {
				return true;
			} else if (!validateId) {
				return true;
			}
		}
		return false;
	}

	private Category getCategoryFromMap(Map<String, String> requestMap, Boolean isAdd) {
		Category category = new Category();
		// ifthis is is true set id
		if (isAdd) {
			category.setId(Integer.parseInt(requestMap.get("id")));
		}
		category.setName(requestMap.get("name"));
		return category;// created model of the category
	}

	@Override
	public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
		// TODO Auto-generated method stub
		try {
			// checking for filtervalue
			if (!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")) {
				log.info("Inside if");
				// only need all those value which contains one or more product in it
				return new ResponseEntity<List<Category>>(categoryDAO.getAllCategory(), HttpStatus.OK);
			}
			return new ResponseEntity<>(categoryDAO.findAll(), HttpStatus.OK);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		try {
			// checking only user can update it
			if (jwtFilter.isAdmin()) {
				// checking if this category doesn't exist in the database
				// before checking above, validating the requestMap
				if (validateCategoryMap(requestMap, true)) {// true is that validating id also
					// fetching the data with that id
					Optional<Category> optional = categoryDAO.findById(Integer.parseInt(requestMap.get("id")));// returns
																												// object
					// of type
					// optional //remove <category> if needed
					// if this category doesnt exist that is null
					if (!optional.isEmpty()) {
						// if everything is ok ,then passing category object in save and using
						// getcategory
						categoryDAO.save(getCategoryFromMap(requestMap, true));// if its true value ,it extracts the id
																				// of category object
						return ErrsUtils.getResponseEntity("Category Updated Successfully", HttpStatus.OK);
					} else {
						return ErrsUtils.getResponseEntity("Category id doesn't exist", HttpStatus.OK);
					}
				}
				// if validation is failed ,returning below
				return ErrsUtils.getResponseEntity(ErrsConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
			} else {
				return ErrsUtils.getResponseEntity(ErrsConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ErrsUtils.getResponseEntity(ErrsConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
