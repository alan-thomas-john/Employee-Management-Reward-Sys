package com.errs.management.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.errs.management.entities.Category;

public interface CategoryDAO extends JpaRepository<Category, Integer> {

	List<Category> getAllCategory();
}
