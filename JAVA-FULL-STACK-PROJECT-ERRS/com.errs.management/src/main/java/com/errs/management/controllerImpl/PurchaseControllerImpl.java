package com.errs.management.controllerImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.errs.management.controller.PurchaseController;
import com.errs.management.entities.Purchase;
import com.errs.management.service.PurchaseService;
import com.errs.management.utils.ErrsUtils;

@RestController
public class PurchaseControllerImpl implements PurchaseController {

	@Autowired
	private PurchaseService purchaseService;

	@Override
	public ResponseEntity<String> addProduct(Purchase purchase) {
		// TODO Auto-generated method stub
		try {
			return purchaseService.addProduct(purchase);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ErrsUtils.getResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<List<Purchase>> getPurchasesByUserId(int userId) {
		try {
			return purchaseService.getPurchaseByUserId(userId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
