package com.openclassrooms.safetyNet.controller;

import com.openclassrooms.safetyNet.exceptions.*;
import com.openclassrooms.safetyNet.service.FireStationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
            NotFoundException.class, AlreadyExistsException.class, MethodArgumentNotValidException.class, InvalidException.class})
    public final ResponseEntity<?> handleException(Exception ex) {

        final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

        if (ex instanceof NotFoundException) {
            logger.error("Not found exception: {}", ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        if (ex instanceof AlreadyExistsException) {
            logger.error("Already exists exception: {}", ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
        if (ex instanceof MethodArgumentNotValidException) {
            logger.error("Method argument not valid exception: {}", ex.getMessage());
            return handleMethodArgumentNotValidException((MethodArgumentNotValidException) ex);
        }

        // ex instanceof InvalidException
        logger.error("Invalid exception: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errorMessages = ex.getAllErrors().stream().map(contentError -> contentError.getObjectName() + " " + contentError.getDefaultMessage()).collect(Collectors.toList());
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }
}