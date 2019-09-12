package com.example.mainapp.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotImplementedException extends RuntimeException {

	public NotImplementedException() {}

	public NotImplementedException(String ex) {
		super(ex);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
