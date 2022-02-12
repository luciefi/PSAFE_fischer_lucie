package com.openclassrooms.safetyNet.controller;

import com.openclassrooms.safetyNet.exceptions.InvalidFormattedFullNameException;
import com.openclassrooms.safetyNet.exceptions.PersonAlreadyExistsException;
import com.openclassrooms.safetyNet.exceptions.PersonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({PersonNotFoundException.class,
            PersonAlreadyExistsException.class,
            MethodArgumentNotValidException.class,
            InvalidFormattedFullNameException.class})
    public final ResponseEntity<?> handleException(Exception ex) {

        if (ex instanceof PersonNotFoundException) {
            return handlePersonNotFoundException();
        }
        if (ex instanceof PersonAlreadyExistsException) {
            return handlePersonAlreadyExistsException();
        }
        if (ex instanceof MethodArgumentNotValidException) {
            return handleMethodArgumentNotValidException((MethodArgumentNotValidException) ex);
        }
        if (ex instanceof InvalidFormattedFullNameException) {
            return handleInvalidFormattedFullNameException();
        }

        return handleExceptionInternal(ex);
    }

    private ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<String> errorMessages = ex.getAllErrors()
                .stream()
                .map(contentError -> contentError.getObjectName() + " " + contentError.getDefaultMessage())
                .collect(Collectors.toList());

        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<?> handlePersonNotFoundException() {
        return new ResponseEntity<>("Person could not be found.", HttpStatus.NOT_FOUND);
    }

    protected ResponseEntity<?> handlePersonAlreadyExistsException() {
        return new ResponseEntity<>("Person already exists.", HttpStatus.CONFLICT);
    }

    protected ResponseEntity<?> handleInvalidFormattedFullNameException() {
        return new ResponseEntity<>("Formatted name is not valid.", HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<?> handleExceptionInternal(Exception ex) {
        return new ResponseEntity<>("Internal error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}