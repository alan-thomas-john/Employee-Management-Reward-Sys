package com.errs.management.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface DashboardsService {

	ResponseEntity<Map<String, Object>> getCount();

	ResponseEntity<Map<String, Object>> getLoggedInUserInfo(String username);

}
