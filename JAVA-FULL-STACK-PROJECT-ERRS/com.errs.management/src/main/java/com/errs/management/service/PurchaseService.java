package com.errs.management.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.errs.management.entities.Purchase;

public interface PurchaseService {
	
	ResponseEntity<String> addProduct(Purchase purchase);

	ResponseEntity<List<Purchase>> getPurchaseByUserId(int userId);

}
