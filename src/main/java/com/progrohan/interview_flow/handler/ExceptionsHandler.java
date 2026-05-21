package com.progrohan.interview_flow.handler;

import com.progrohan.interview_flow.dto.ExceptionResponseDto;
import com.progrohan.interview_flow.exception.AttemptNotEndedException;
import com.progrohan.interview_flow.exception.InvalidStoredJsonException;
import com.progrohan.interview_flow.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponseDto(ex.getMessage()));

    }

    @ExceptionHandler(InvalidStoredJsonException.class)
    public ResponseEntity<ExceptionResponseDto> handleInvalidStoredJsonException(InvalidStoredJsonException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponseDto(ex.getMessage()));

    }

    @ExceptionHandler(AttemptNotEndedException.class)
    public ResponseEntity<ExceptionResponseDto> handleInvalidStoredJsonException(AttemptNotEndedException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponseDto(ex.getMessage()));

    }



}
