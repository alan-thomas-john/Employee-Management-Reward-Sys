package com.errs.management.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.errs.management.entities.Purchase;

@RequestMapping(path = "/purchase")
public interface PurchaseController {

	@PostMapping(path = "/order")
	public ResponseEntity<String> addProduct(@RequestBody Purchase purchase);

	@GetMapping("/order/{userId}")
    public ResponseEntity<List<Purchase>> getPurchasesByUserId(@PathVariable int userId);
}
