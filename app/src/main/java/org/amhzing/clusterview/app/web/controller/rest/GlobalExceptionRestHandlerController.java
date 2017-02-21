package org.amhzing.clusterview.app.web.controller.rest;

import org.amhzing.clusterview.app.web.model.ValidationError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.amhzing.clusterview.app.web.model.ValidationErrorBuilder.fromBindingErrors;

@ControllerAdvice
public class GlobalExceptionRestHandlerController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        final ValidationError error = fromBindingErrors(exception.getBindingResult());

        return super.handleExceptionInternal(exception, error, headers, status, request);
    }
}
