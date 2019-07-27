package com.example.mainapp.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

	public NotFoundException() {}

	public NotFoundException(String ex) {
		super(ex);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
