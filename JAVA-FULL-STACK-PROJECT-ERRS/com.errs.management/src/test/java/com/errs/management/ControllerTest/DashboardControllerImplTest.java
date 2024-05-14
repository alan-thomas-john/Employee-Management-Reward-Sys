package com.errs.management.ControllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.errs.management.controllerImpl.DashboardsControllerImpl;
import com.errs.management.service.DashboardsService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DashboardControllerImplTest {
	@Mock
	private DashboardsService dashboardsService;

	@InjectMocks
	private DashboardsControllerImpl dashboardsController;

	@Mock
	private SecurityContext securityContext;

	@Mock
	private Authentication authentication;

	@BeforeEach
	public void setUp() {
		SecurityContextHolder.setContext(securityContext);
	}

	@Test
	public void testGetCount() {
		// Arrange
		ResponseEntity<Map<String, Object>> expectedResponse = ResponseEntity.ok(Map.of("count", 10));
		when(dashboardsService.getCount()).thenReturn(expectedResponse);

		// Act
		ResponseEntity<Map<String, Object>> response = dashboardsController.getCount();

		// Assert
		assertNotNull(response);
		assertEquals(expectedResponse, response);
		verify(dashboardsService, times(1)).getCount();
	}

	@Test
	public void testGetLoggedInUserInfo_SuccessfulAuthentication() {
		// Arrange
		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.isAuthenticated()).thenReturn(true);
		when(authentication.getName()).thenReturn("user1");
		ResponseEntity<Map<String, Object>> expectedResponse = ResponseEntity.ok(Map.of("username", "user1"));
		when(dashboardsService.getLoggedInUserInfo("user1")).thenReturn(expectedResponse);

		// Act
		ResponseEntity<Map<String, Object>> response = dashboardsController.getLoggedInUserInfo();

		// Assert
		assertNotNull(response);
		assertEquals(expectedResponse, response);
		verify(dashboardsService, times(1)).getLoggedInUserInfo("user1");
	}

	@Test
	public void testGetLoggedInUserInfo_NoAuthentication() {
		// Arrange
		when(securityContext.getAuthentication()).thenReturn(null);

		// Act
		ResponseEntity<Map<String, Object>> response = dashboardsController.getLoggedInUserInfo();

		// Assert
		assertNotNull(response);
		assertEquals(401, response.getStatusCode().value());
		assertTrue(response.getBody().containsKey("error"));
		assertEquals("User not authenticated", response.getBody().get("error"));
	}

	@Test
	public void testGetLoggedInUserInfo_NotAuthenticated() {
		// Arrange
		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.isAuthenticated()).thenReturn(false);

		// Act
		ResponseEntity<Map<String, Object>> response = dashboardsController.getLoggedInUserInfo();

		// Assert
		assertNotNull(response);
		assertEquals(401, response.getStatusCode().value());
		assertTrue(response.getBody().containsKey("error"));
		assertEquals("User not authenticated", response.getBody().get("error"));
	}

	@Test
	public void testGetLoggedInUserInfo_ThrowsException() {
		// Arrange
		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.isAuthenticated()).thenReturn(true);
		when(authentication.getName()).thenThrow(new AuthenticationException("Failure during authentication") {
			private static final long serialVersionUID = 1L;
		});

		// Act
		ResponseEntity<Map<String, Object>> response = dashboardsController.getLoggedInUserInfo();

		// Assert
		assertNotNull(response);
		assertEquals(500, response.getStatusCode().value());
		assertTrue(response.getBody().containsKey("error"));
		assertEquals("Internal Server Error", response.getBody().get("error"));
		verify(authentication, times(1)).getName();
	}

}
