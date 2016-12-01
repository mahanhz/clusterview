package org.amhzing.clusterview.web.controller.se;

import com.fasterxml.uuid.Generators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@ControllerAdvice
public class GlobalExceptionHandlerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandlerController.class);

    @ExceptionHandler
    public ModelAndView handleException(final HttpServletRequest request, final Throwable throwable) {

        final UUID errorId = Generators.timeBasedGenerator().generate();
        LOGGER.error("ErrorId: {} references the following error: ", errorId, throwable);

        final ModelAndView mav = new ModelAndView();
        mav.addObject("cameFrom", request.getRequestURI());
        mav.addObject("errorId", errorId);
        mav.setViewName("se/error");

        return mav;
    }
}
