package com.errs.management.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/dashboard")
public interface DashboardsController {

	@GetMapping(path = "/details")
	ResponseEntity<Map<String, Object>> getCount();

	@GetMapping(path = "/user-info") 
	ResponseEntity<Map<String, Object>> getLoggedInUserInfo();
}
