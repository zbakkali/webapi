package com.example.webapi.api;

import com.example.webapi.exception.AbstractNotFoundException;
import com.example.webapi.model.ErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class WebApiExceptionHandler extends ResponseEntityExceptionHandler {

    /*@ExceptionHandler(value = AbstractNotFoundException.class)
    protected ResponseEntity<Object> handleNotFoundException(AbstractNotFoundException e, WebRequest request) {
        ErrorDto error = new ErrorDto()
                .status(HttpStatus.NOT_FOUND.value())
                .detail(e.getMessage());
        return handleExceptionInternal(e, error, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception e, WebRequest request) {
        ErrorDto error = new ErrorDto()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail(e.getMessage());
        return handleExceptionInternal(e, error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }*/

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(HttpMessageNotReadableException e) {
        logger.warn("Returning HTTP 400 Bad Request", e);
    }
}
