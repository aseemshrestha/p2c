package com.p2c.p2c.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler( BadRequestException.class )
    public final ResponseEntity handleBadRequestException(Exception ex, WebRequest request)
    {
        ExceptionResponse exceptionResponse =
            new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false),
                                  HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler( DataIntegrityViolationException.class )
    protected ResponseEntity handleConstraintViolation(DataIntegrityViolationException ex, WebRequest request)
    {
        String msg = ex.getRootCause().getMessage();
        String outputMsg = "";
        if (msg.contains("Duplicate entry")) {
            switch (outputMsg = msg.contains("@") ? "Email is taken" : "User name is taken") {
            }
        }
        ExceptionResponse exceptionResponse =
            new ExceptionResponse(new Date(), outputMsg,
                                  request.getDescription(false),
                                  HttpStatus.CONFLICT.value());
        return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler( ResourceNotFoundException.class )
    public final ResponseEntity<Object> handleResourceNotFound(Exception ex, WebRequest request)
    {
        ExceptionResponse exceptionResponse =
            new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false),
                                  HttpStatus.NOT_FOUND.value());
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler( Exception.class )
    public final ResponseEntity<Object> handleAlException(Exception ex, WebRequest request)
    {
        ExceptionResponse exceptionResponse =
            new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false),
                                  HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
