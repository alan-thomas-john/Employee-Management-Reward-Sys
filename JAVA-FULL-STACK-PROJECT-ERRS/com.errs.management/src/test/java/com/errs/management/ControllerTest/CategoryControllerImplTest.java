package com.errs.management.ControllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.errs.management.controllerImpl.CategoryControllerImpl;
import com.errs.management.entities.Category;
import com.errs.management.service.CategoryService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CategoryControllerImplTest {

	@InjectMocks
	private CategoryControllerImpl categoryController;

	@Mock
	private CategoryService categoryService;

	@Test
	public void testAddNewCategory_Success() throws Exception {
		// Arrange
		Map<String, String> requestMap = new HashMap<>();
		requestMap.put("name", "Electronics");
		ResponseEntity<String> expectedResponse = new ResponseEntity<>("Category added", HttpStatus.OK);
		when(categoryService.addNewCategory(requestMap)).thenReturn(expectedResponse);

		// Act
		ResponseEntity<String> response = categoryController.addNewCategory(requestMap);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(categoryService).addNewCategory(requestMap);
	}

	@Test
	public void testAddNewCategory_Failure() throws Exception {
		// Arrange
		Map<String, String> requestMap = new HashMap<>();
		when(categoryService.addNewCategory(requestMap)).thenThrow(new RuntimeException());

		// Act
		ResponseEntity<String> response = categoryController.addNewCategory(requestMap);

		// Assert
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		verify(categoryService).addNewCategory(requestMap);
	}

	@Test
	public void testGetAllCategory_Success() throws Exception {
		// Arrange
		String filterValue = "test";
		List<Category> categories = new ArrayList<>();
		categories.add(new Category());
		when(categoryService.getAllCategory(filterValue)).thenReturn(new ResponseEntity<>(categories, HttpStatus.OK));

		// Act
		ResponseEntity<List<Category>> response = categoryController.getAllCategory(filterValue);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().size());
		verify(categoryService).getAllCategory(filterValue);
	}

	@Test
	public void testGetAllCategory_Failure() throws Exception {
		// Arrange
		String filterValue = "test";
		when(categoryService.getAllCategory(filterValue)).thenThrow(new RuntimeException());

		// Act
		ResponseEntity<List<Category>> response = categoryController.getAllCategory(filterValue);

		// Assert
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertTrue(response.getBody().isEmpty());
		verify(categoryService).getAllCategory(filterValue);
	}

	@Test
	public void testUpdatedCategory_Success() throws Exception {
		// Arrange
		Map<String, String> requestMap = new HashMap<>();
		requestMap.put("name", "Electronics");
		ResponseEntity<String> expectedResponse = new ResponseEntity<>("Category updated", HttpStatus.OK);
		when(categoryService.updateCategory(requestMap)).thenReturn(expectedResponse);

		// Act
		ResponseEntity<String> response = categoryController.updatedCategory(requestMap);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(categoryService).updateCategory(requestMap);
	}

	@Test
	public void testUpdatedCategory_Failure() throws Exception {
		// Arrange
		Map<String, String> requestMap = new HashMap<>();
		when(categoryService.updateCategory(requestMap)).thenThrow(new RuntimeException());

		// Act
		ResponseEntity<String> response = categoryController.updatedCategory(requestMap);

		// Assert
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		verify(categoryService).updateCategory(requestMap);
	}
}
