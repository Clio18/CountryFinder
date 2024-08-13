package com.obolonyk.countryfinder.web;

import com.obolonyk.countryfinder.exception.CountryNotFoundException;
import com.obolonyk.countryfinder.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<Response<String>> handleNotFoundException(CountryNotFoundException ex) {

        Response<String> response = Response.<String>builder()
                .type(Response.Type.ERROR)
                .description(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
