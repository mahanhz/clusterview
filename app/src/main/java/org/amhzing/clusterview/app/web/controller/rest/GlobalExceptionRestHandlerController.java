package org.amhzing.clusterview.app.web.controller.rest;

import com.fasterxml.uuid.Generators;
import org.amhzing.clusterview.app.exception.NotFoundException;
import org.amhzing.clusterview.app.web.model.error.ErrorResponse;
import org.amhzing.clusterview.app.web.model.error.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static org.amhzing.clusterview.app.web.model.error.ValidationErrorBuilder.fromBindingErrors;

@RestControllerAdvice(basePackageClasses = { GlobalExceptionRestHandlerController.class })
public class GlobalExceptionRestHandlerController extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionRestHandlerController.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        final ValidationError error = fromBindingErrors(exception.getBindingResult());

        return super.handleExceptionInternal(exception, error, headers, status, request);
    }

    @ExceptionHandler
    public ErrorResponse handleException(final HttpServletRequest request,
                                         final HttpServletResponse response,
                                         final Throwable throwable) {

        final UUID errorId = Generators.timeBasedGenerator().generate();
        LOGGER.error("ErrorId: {} references the following error: ", errorId, throwable);

        final ErrorResponse errorResponse = ErrorResponse.create(errorId.toString(),
                                                                 "" + response.getStatus(),
                                                                 request.getRequestURI(),
                                                                 "");
        return errorResponse;
    }

    // This is here if a method fails @PreAuthorize
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResponse accessDenied(final HttpServletRequest request) {
        final ErrorResponse errorResponse = ErrorResponse.create("",
                                                                 HttpStatus.FORBIDDEN.toString(),
                                                                 request.getRequestURI(),
                                                                 HttpStatus.FORBIDDEN.getReasonPhrase());
        return errorResponse;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse notFound(final HttpServletRequest request) {
        final ErrorResponse errorResponse = ErrorResponse.create("",
                                                                 HttpStatus.NOT_FOUND.toString(),
                                                                 request.getRequestURI(),
                                                                 HttpStatus.NOT_FOUND.getReasonPhrase());
        return errorResponse;
    }
}
