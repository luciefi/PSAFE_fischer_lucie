package com.openclassrooms.safetyNet.controller;

import com.openclassrooms.safetyNet.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({
            NotFoundException.class, AlreadyExistsException.class, MethodArgumentNotValidException.class, InvalidException.class, Exception.class})
    public final ResponseEntity<?> handleException(Exception ex) {

        if (ex instanceof NotFoundException) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        if (ex instanceof AlreadyExistsException) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
        if (ex instanceof MethodArgumentNotValidException) {
            return handleMethodArgumentNotValidException((MethodArgumentNotValidException) ex);
        }

        if (ex instanceof InvalidException) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return handleExceptionInternal(ex);
    }

    private ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errorMessages = ex.getAllErrors().stream().map(contentError -> contentError.getObjectName() + " " + contentError.getDefaultMessage()).collect(Collectors.toList());
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }


    protected ResponseEntity<?> handleExceptionInternal(Exception ex) {
        return new ResponseEntity<>("Internal error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}