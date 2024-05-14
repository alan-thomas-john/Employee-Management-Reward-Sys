package com.errs.management.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.errs.management.entities.RecognitionHandler;

public interface RecognitionHandlerDAO extends JpaRepository<RecognitionHandler, Integer> {

	@Query(value = "SELECT * FROM public.recognition_handler_table WHERE user_id = :userId", nativeQuery = true)
	List<RecognitionHandler> findByUserIdNative(int userId);

	@Query(value = "SELECT COUNT(*) FROM recognition_handler_table WHERE user_id=:userId", nativeQuery = true)
	int countByUserIdNative(int userId);

}
