package com.errs.management.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.errs.management.dto.ProductDTO;
import com.errs.management.entities.Product;

public interface ProductDAO extends JpaRepository<Product, Integer> {

	List<ProductDTO> getAllProduct();

	@Modifying // to update record
	@Transactional
	Integer updateProductStatus(@Param("status") String status, @Param("id") Integer id);

	List<ProductDTO> getProductByCategory(@Param("id") Integer id);

	ProductDTO getProductById(@Param("id") Integer id);
}
