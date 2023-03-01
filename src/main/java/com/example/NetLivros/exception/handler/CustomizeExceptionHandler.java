package com.example.NetLivros.exception.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.example.NetLivros.exception.ExceptionResponse;
import com.example.NetLivros.exception.ResourceAlreadyExistsException;
import com.example.NetLivros.exception.ResourceNotFoundException;

@ControllerAdvice
@RestController
public class CustomizeExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleGenericException(Exception ex, WebRequest req) {
		ExceptionResponse exceptionResponse = new ExceptionResponse("Internal Server Error", new Date(),
				ex.getMessage(), req.getDescription(false));
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleResourceNotFoundException(Exception ex, WebRequest req) {
		ExceptionResponse exceptionResponse = new ExceptionResponse("Resource Not Found Exception", new Date(),
				ex.getMessage(), req.getDescription(false));
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public final ResponseEntity<ExceptionResponse> handleResourceAlreadyExistsException(Exception ex, WebRequest req) {
		ExceptionResponse exceptionResponse = new ExceptionResponse("Resource Already Exists Exception", new Date(),
				ex.getMessage(), req.getDescription(false));
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.CONFLICT);
	}

}
