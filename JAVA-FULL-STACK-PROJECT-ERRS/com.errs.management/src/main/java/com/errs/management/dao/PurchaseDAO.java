package com.errs.management.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.errs.management.entities.Purchase;

public interface PurchaseDAO extends JpaRepository<Purchase, Integer>{

	
	List<Purchase> findByUserUserId(int userId);
}
