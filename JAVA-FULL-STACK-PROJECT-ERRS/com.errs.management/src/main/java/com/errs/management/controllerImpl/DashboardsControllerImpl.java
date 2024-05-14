package com.errs.management.controllerImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import com.errs.management.controller.DashboardsController;
import com.errs.management.service.DashboardsService;

@RestController
public class DashboardsControllerImpl implements DashboardsController {

	@Autowired
	DashboardsService dashboardsService;

	@Override
	public ResponseEntity<Map<String, Object>> getCount() {
		// TODO Auto-generated method stub
		return dashboardsService.getCount();
	}

	@Override
	public ResponseEntity<Map<String, Object>> getLoggedInUserInfo() {
		// TODO Auto-generated method stub
		 Map<String, Object> result = new HashMap<>();
		    try {
		        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		        if (authentication != null && authentication.isAuthenticated()) {
		            String username = authentication.getName();
		            return dashboardsService.getLoggedInUserInfo(username);
		        } else {
		            result.put("error", "User not authenticated");
		            return ResponseEntity.status(401).body(result);
		        }
		    } catch (Exception e) {
		        result.put("error", "Internal Server Error");
		        return ResponseEntity.status(500).body(result);
		    }
	}
}
