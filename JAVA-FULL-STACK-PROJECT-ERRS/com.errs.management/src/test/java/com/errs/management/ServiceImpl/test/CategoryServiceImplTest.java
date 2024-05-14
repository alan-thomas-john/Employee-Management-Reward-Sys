package com.errs.management.ServiceImpl.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.errs.management.dao.CategoryDAO;
import com.errs.management.entities.Category;
import com.errs.management.jwt.JwtFilter;
import com.errs.management.serviceImpl.CategoryServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {
	@Mock
	private CategoryDAO categoryDAO;

	@Mock
	private JwtFilter jwtFilter;

	@InjectMocks
	private CategoryServiceImpl categoryService;

	@Test
	public void testAddNewCategory() {
		// Arrange
		// Mocking admin access
		when(jwtFilter.isAdmin()).thenReturn(true);

		// Mocking category data
		Map<String, String> requestMap = new HashMap<>();
		requestMap.put("name", "TestCategory");

		// Mocking DAO behavior
		when(categoryDAO.save(any())).thenReturn(mock(Category.class)); // assuming save returns a Category object

		// Act
		// Executing the method under test
		ResponseEntity<String> responseEntity = categoryService.addNewCategory(requestMap);

		// Assert
		// Assertions
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("{\"message\":\"Category Added Successfully\"}", responseEntity.getBody());

		// Verifying that DAO save method was called
		verify(categoryDAO, times(1)).save(any());
	}

	@Test
	public void testAddNewCategoryUnauthorizedAccess() {
		// Arrange
		// Mocking non-admin access
		when(jwtFilter.isAdmin()).thenReturn(false);

		// Act
		// Executing the method under test
		ResponseEntity<String> responseEntity = categoryService.addNewCategory(new HashMap<>());

		// Assert
		// Assertions
		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
		assertEquals("{\"message\":\"Unauthorized Access.\"}", responseEntity.getBody());

		// Verifying that DAO save method was not called
		verify(categoryDAO, never()).save(any());

	}

	@Test
	public void testAddNewCategoryValidRequestWithoutID() {
		// Arrange
		when(jwtFilter.isAdmin()).thenReturn(true);

		Map<String, String> requestMap = new HashMap<>();
		requestMap.put("name", "TestCategory");

		when(categoryDAO.save(any())).thenReturn(mock(Category.class));

		// Act
		ResponseEntity<String> responseEntity = categoryService.addNewCategory(requestMap);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("{\"message\":\"Category Added Successfully\"}", responseEntity.getBody());
	}

	@Test
	public void testAddNewCategoryInvalidRequestMissingName() {
		// Arrange
		when(jwtFilter.isAdmin()).thenReturn(true);

		Map<String, String> requestMap = new HashMap<>();

		// Act
		ResponseEntity<String> responseEntity = categoryService.addNewCategory(requestMap);

		// Assert
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
		assertEquals("{\"message\":\"Something Went Wrong.\"}", responseEntity.getBody());
	}

	@Test
	public void testAddNewCategoryHandlesUnexpectedException() {
		// Arrange
		when(jwtFilter.isAdmin()).thenReturn(true);
		doThrow(RuntimeException.class).when(categoryDAO).save(any());

		Map<String, String> requestMap = new HashMap<>();
		requestMap.put("name", "TestCategory");

		// Act
		ResponseEntity<String> responseEntity = categoryService.addNewCategory(requestMap);

		// Assert
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
		assertEquals("{\"message\":\"Something Went Wrong.\"}", responseEntity.getBody());
	}

	@Test
	public void testGetAllCategoryWithTrueFilter() {
		// Arrange
		List<Category> expectedCategories = new ArrayList<>();
		when(categoryDAO.getAllCategory()).thenReturn(expectedCategories);

		// Act
		ResponseEntity<List<Category>> response = categoryService.getAllCategory("true");

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedCategories, response.getBody());
		verify(categoryDAO).getAllCategory();
		verify(categoryDAO, never()).findAll();
	}

	@Test
	public void testGetAllCategoryWithFalseFilter() {
		// Arrange
		List<Category> expectedCategories = new ArrayList<>();
		when(categoryDAO.findAll()).thenReturn(expectedCategories);

		// Act
		ResponseEntity<List<Category>> response = categoryService.getAllCategory("false");

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedCategories, response.getBody());
		verify(categoryDAO, never()).getAllCategory();
		verify(categoryDAO).findAll();
	}

	@Test
	public void testGetAllCategoryWithNullFilter() {
		// Arrange
		List<Category> expectedCategories = new ArrayList<>();
		when(categoryDAO.findAll()).thenReturn(expectedCategories);

		// Act
		ResponseEntity<List<Category>> response = categoryService.getAllCategory(null);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedCategories, response.getBody());
		verify(categoryDAO, never()).getAllCategory();
		verify(categoryDAO).findAll();
	}

	@Test
	public void testGetAllCategoryWithException() {
		// Arrange
		when(categoryDAO.findAll()).thenThrow(new RuntimeException());

		// Act
		ResponseEntity<List<Category>> response = categoryService.getAllCategory("false");

		// Assert
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals(0, response.getBody().size());
	}

	@Test
	public void testUpdateCategoryByAdminWithValidDataAndExistingCategory() {
		// Arrange
		Map<String, String> requestMap = new HashMap<>();
		requestMap.put("id", "1");
		requestMap.put("name", "Electronics");
		when(jwtFilter.isAdmin()).thenReturn(true);
		when(categoryDAO.findById(1)).thenReturn(Optional.of(new Category()));
		when(categoryDAO.save(any())).thenReturn(new Category());

		// Act
		ResponseEntity<String> response = categoryService.updateCategory(requestMap);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("{\"message\":\"Category Updated Successfully\"}", response.getBody());
	}

	@Test
	public void testUpdateCategoryByAdminWithValidDataButCategoryDoesNotExist() {
		// Arrange
		Map<String, String> requestMap = new HashMap<>();
		requestMap.put("id", "2");
		requestMap.put("name", "Books");
		when(jwtFilter.isAdmin()).thenReturn(true);
		when(categoryDAO.findById(2)).thenReturn(Optional.empty());

		// Act
		ResponseEntity<String> response = categoryService.updateCategory(requestMap);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("{\"message\":\"Category id doesn't exist\"}", response.getBody());
	}

	@Test
	public void testUpdateCategoryByAdminWithInvalidData() {
		// Arrange
		Map<String, String> requestMap = new HashMap<>();
		when(jwtFilter.isAdmin()).thenReturn(true);

		// Act
		ResponseEntity<String> response = categoryService.updateCategory(requestMap);

		// Assert
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("{\"message\":\"Invalid Data.\"}", response.getBody());
	}

	@Test
	public void testUpdateCategoryByNonAdmin() {
		// Arrange
		Map<String, String> requestMap = new HashMap<>();
		when(jwtFilter.isAdmin()).thenReturn(false);

		// Act
		ResponseEntity<String> response = categoryService.updateCategory(requestMap);

		// Assert
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertEquals("{\"message\":\"Unauthorized Access.\"}", response.getBody());
	}

	@Test
	public void testUpdateCategoryExceptionScenario() {
		// Arrange
		Map<String, String> requestMap = new HashMap<>();
		requestMap.put("id", "1");
		requestMap.put("name", "Books");
		when(jwtFilter.isAdmin()).thenReturn(true);
		when(categoryDAO.findById(1)).thenThrow(new RuntimeException());

		// Act
		ResponseEntity<String> response = categoryService.updateCategory(requestMap);

		// Assert
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals("{\"message\":\"Something Went Wrong.\"}", response.getBody());
	}

	@Test
	public void testValidateCategoryMapWithIdAndValidateIdFalse() {
		// Arrange
		Map<String, String> requestMap = new HashMap<>();
		requestMap.put("id", "123");
		requestMap.put("name", "Electronics");

		// Act
		// The validateId is false, so it should not check for the id even though it's
		// present.
		boolean result = categoryService.validateCategoryMap(requestMap, false);

		// Assert
		assertTrue(result);
	}

	@Test
	public void testValidateCategoryMapWithOnlyNameAndValidateIdFalse() {
		// Arrange
		Map<String, String> requestMap = new HashMap<>();
		requestMap.put("name", "Electronics");

		// Act
		boolean result = categoryService.validateCategoryMap(requestMap, false);

		// Assert
		assertTrue(result, "The validation should pass when only name is provided and validateId is false.");
	}

	@Test
	public void testValidateCategoryMapWithoutIdValidation() {
		// Arrange
		CategoryServiceImpl service = new CategoryServiceImpl();
		Map<String, String> requestMap = new HashMap<>();
		requestMap.put("name", "TestCategory");
		requestMap.put("id", "1"); // ID is present but should be ignored

		// Act
		boolean result = service.validateCategoryMap(requestMap, false);

		// Assert
		assertTrue(result,
				"validateCategoryMap should return true when 'validateId' is false and both 'name' and 'id' are present");
	}

	@Test
	public void testValidateCategoryMapWithIdAndValidateIdTrue() {
		// Arrange
		Map<String, String> requestMap = new HashMap<>();
		requestMap.put("id", "123");
		requestMap.put("name", "Electronics");

		// Act
		boolean result = categoryService.validateCategoryMap(requestMap, true);

		// Assert
		assertTrue(result, "The validation should pass when both name and id are provided and validateId is true.");
	}

	@Test
	public void testValidateCategoryMapWithInvalidIdAndValidateIdFalse() {
		// Arrange
		Map<String, String> requestMap = new HashMap<>();
		requestMap.put("name", "Electronics");
		requestMap.put("id", ""); // Invalid ID but should be ignored

		// Act
		boolean result = categoryService.validateCategoryMap(requestMap, false);

		// Assert
		assertTrue(result, "The validation should pass even with an invalid 'id' when 'validateId' is false.");
	}

	@Test
	public void testValidateCategoryMapWithMissingIdAndValidateIdTrue() {
		// Arrange
		Map<String, String> requestMap = new HashMap<>();
		requestMap.put("name", "Electronics");

		// Act
		boolean result = categoryService.validateCategoryMap(requestMap, true);

		// Assert
		assertFalse(result, "Validation should fail because 'id' is missing but 'validateId' is true.");
	}

}
