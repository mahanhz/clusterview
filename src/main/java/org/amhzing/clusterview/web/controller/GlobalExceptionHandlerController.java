package org.amhzing.clusterview.web.controller;

import com.fasterxml.uuid.Generators;
import org.amhzing.clusterview.exception.ClusterNotFoundException;
import org.amhzing.clusterview.exception.GroupNotFoundException;
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
    private static final String ERROR_VIEW = "error";

    @ExceptionHandler
    public ModelAndView handleException(final HttpServletRequest request, final Throwable throwable) {

        final UUID errorId = Generators.timeBasedGenerator().generate();
        LOGGER.error("ErrorId: {} references the following error: ", errorId, throwable);

        final ModelAndView mav = new ModelAndView();
        mav.addObject("cameFrom", request.getRequestURI());
        mav.addObject("errorId", errorId);
        mav.setViewName(ERROR_VIEW);

        return mav;
    }

    @ExceptionHandler(ClusterNotFoundException.class)
    public ModelAndView clusterNotFound(final ClusterNotFoundException ex) {
        return customMessage("Cluster " + ex.getCluster() + " could not be found");
    }

    @ExceptionHandler(GroupNotFoundException.class)
    public ModelAndView groupNotFound(final GroupNotFoundException ex) {
        return customMessage("Group in cluster " + ex.getCluster() + " could not be found");
    }

    private ModelAndView customMessage(final String message) {
        final ModelAndView mav = new ModelAndView();
        mav.addObject("customMessage", message);
        mav.setViewName(ERROR_VIEW);

        return mav;
    }
}
