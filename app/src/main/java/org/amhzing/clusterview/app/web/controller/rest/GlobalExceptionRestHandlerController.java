package org.amhzing.clusterview.app.web.controller.rest;

import com.fasterxml.uuid.Generators;
import com.google.common.collect.ImmutableMap;
import org.amhzing.clusterview.app.exception.NotFoundException;
import org.amhzing.clusterview.app.web.model.error.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static org.amhzing.clusterview.app.web.model.error.ErrorResponse.*;

@RestControllerAdvice
public class GlobalExceptionRestHandlerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionRestHandlerController.class);

    @ExceptionHandler
    public ErrorResponse handleException(final HttpServletRequest request,
                                         final HttpServletResponse response,
                                         final Throwable throwable) {

        final UUID errorId = Generators.timeBasedGenerator().generate();
        LOGGER.error("ErrorId: {} references the following error: ", errorId, throwable);

        final ImmutableMap<String, Object> errorAttributes = ImmutableMap.of(STATUS, response.getStatus(),
                                                                             PATH, request.getRequestURI());

        return ErrorResponse.create(errorId.toString(), errorAttributes);
    }

    // This is here if a method fails @PreAuthorize
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResponse accessDenied(final HttpServletRequest request) {

        final ImmutableMap<String, Object> errorAttributes = ImmutableMap.of(STATUS, HttpStatus.FORBIDDEN.value(),
                                                                             PATH, request.getRequestURI(),
                                                                             MESSAGE, HttpStatus.FORBIDDEN.getReasonPhrase());

        return ErrorResponse.create("", errorAttributes);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse notFound(final HttpServletRequest request) {
        final ImmutableMap<String, Object> errorAttributes = ImmutableMap.of(STATUS, HttpStatus.NOT_FOUND.value(),
                                                                             PATH, request.getRequestURI(),
                                                                             MESSAGE, HttpStatus.NOT_FOUND.getReasonPhrase());

        return ErrorResponse.create("", errorAttributes);
    }
}
