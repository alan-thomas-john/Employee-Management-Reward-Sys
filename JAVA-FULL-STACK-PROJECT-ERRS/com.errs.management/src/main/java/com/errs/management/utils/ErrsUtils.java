package com.errs.management.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrsUtils {
	private ErrsUtils() {

	}

	public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus) {
		return new ResponseEntity<String>("{\"message\":\"" + responseMessage + "\"}", httpStatus);
	}

}
