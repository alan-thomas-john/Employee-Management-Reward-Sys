package com.errs.management.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.errs.management.dao.CategoryDAO;
import com.errs.management.dao.ProductDAO;
import com.errs.management.dao.UserDAO;
import com.errs.management.entities.User;
import com.errs.management.service.DashboardsService;

@Service
public class DashboardsServiceImpl implements DashboardsService {

	@Autowired
	CategoryDAO categoryDAO;

	@Autowired
	ProductDAO productDAO;

	@Autowired
	UserDAO userDAO;

	@Override
	public ResponseEntity<Map<String, Object>> getCount() {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		map.put("category", categoryDAO.count());
		map.put("product", productDAO.count());
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Map<String, Object>> getLoggedInUserInfo(String username) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<>();
		try {

			User user = userDAO.findByUsername(username);

			if (user != null) {
				result.put("userId", user.getUserId());
				result.put("name", user.getName());
				result.put("points", user.getPoints());
				// Add any other information if needed
			}

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			// Handling exceptions and return an error response
			return ResponseEntity.status(500).body(result);
		}
	}
}
