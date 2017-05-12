package org.amhzing.clusterview.appui.web.controller.exception;

import com.fasterxml.uuid.Generators;
import org.amhzing.clusterview.adapter.web.NotFoundException;
import org.amhzing.clusterview.appui.web.controller.UIGlobalExceptionMarker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@ControllerAdvice(basePackageClasses = { UIGlobalExceptionMarker.class })
public class GlobalExceptionHandlerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandlerController.class);
    public static final String ERROR_VIEW = "error";
    public static final String CUSTOM_MESSAGE_KEY = "customMessage";
    public static final String ERROR_ID_KEY = "errorId";
    public static final String CAME_FROM = "cameFrom";

    @ExceptionHandler
    public ModelAndView handleException(final HttpServletRequest request, final Throwable throwable) {

        final UUID errorId = Generators.timeBasedGenerator().generate();
        LOGGER.error("ErrorId: {} references the following error: ", errorId, throwable);

        final ModelAndView mav = new ModelAndView();
        mav.addObject(CAME_FROM, request.getRequestURI());
        mav.addObject(ERROR_ID_KEY, errorId);
        mav.setViewName(ERROR_VIEW);

        return mav;
    }

    // This is here if a method fails @PreAuthorize
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView accessDenied() {
        return new ModelAndView("error/403");
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView notFound(final HttpServletRequest request) {
        return customMessage(request, HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    private ModelAndView customMessage(final HttpServletRequest request, final String message) {
        final ModelAndView mav = new ModelAndView();
        mav.addObject(CAME_FROM, request.getRequestURI());
        mav.addObject(CUSTOM_MESSAGE_KEY, message);
        mav.setViewName(ERROR_VIEW);

        return mav;
    }
}
