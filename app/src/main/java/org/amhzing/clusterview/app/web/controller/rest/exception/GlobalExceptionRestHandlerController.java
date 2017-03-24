package org.amhzing.clusterview.app.web.controller.rest.exception;

import com.fasterxml.uuid.Generators;
import com.google.common.collect.ImmutableMap;
import org.amhzing.clusterview.app.exception.NotFoundException;
import org.amhzing.clusterview.app.web.MediaTypes;
import org.amhzing.clusterview.app.web.controller.rest.GlobalExceptionRestMarker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static org.amhzing.clusterview.app.web.controller.rest.exception.VndErrorFactory.vndErrors;

@RestControllerAdvice(basePackageClasses = { GlobalExceptionRestMarker.class })
public class GlobalExceptionRestHandlerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionRestHandlerController.class);

    public static final String ERROR_ID = "errorId";
    public static final String STATUS = "status";
    public static final String PATH = "path";
    public static final String MESSAGE = "message";

    @ExceptionHandler
    public ResponseEntity<VndErrors> handleException(final HttpServletRequest request,
                                                     final HttpServletResponse response,
                                                     final Throwable throwable) {

        final UUID errorId = Generators.timeBasedGenerator().generate();
        LOGGER.error("ErrorId: {} references the following error: ", errorId, throwable);

        final HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
        final ImmutableMap<String, String> errorAttributes = ImmutableMap.of(ERROR_ID, errorId.toString(),
                                                                             STATUS, httpStatus.toString(),
                                                                             PATH, request.getRequestURI(),
                                                                             MESSAGE, throwable.getMessage());

        return new ResponseEntity<>(vndErrors(errorAttributes), headers(), httpStatus);
    }

    // This is here if a method fails @PreAuthorize
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<VndErrors> accessDenied(final HttpServletRequest request) {

        final HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        final ImmutableMap<String, String> errorAttributes = ImmutableMap.of(STATUS, httpStatus.toString(),
                                                                             PATH, request.getRequestURI(),
                                                                             MESSAGE, httpStatus.getReasonPhrase());

        return new ResponseEntity<>(vndErrors(errorAttributes), headers(), httpStatus);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<VndErrors> notFound(final HttpServletRequest request) {

        final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        final ImmutableMap<String, String> errorAttributes = ImmutableMap.of(STATUS, httpStatus.toString(),
                                                                             PATH, request.getRequestURI(),
                                                                             MESSAGE, httpStatus.getReasonPhrase());

        return new ResponseEntity<>(vndErrors(errorAttributes), headers(), httpStatus);
    }

    private HttpHeaders headers() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaTypes.APPLICATION_VND_ERROR_JSON);
        return headers;
    }
}
