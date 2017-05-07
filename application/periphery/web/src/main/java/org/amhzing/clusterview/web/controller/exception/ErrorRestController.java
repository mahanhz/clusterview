package org.amhzing.clusterview.web.controller.exception;

import org.amhzing.clusterview.infra.annotation.ConditionalOnRestEnabled;
import org.amhzing.clusterview.web.timing.LogExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static org.amhzing.clusterview.web.MediaTypes.APPLICATION_VND_ERROR_JSON_VALUE;
import static org.amhzing.clusterview.web.controller.exception.VndErrorFactory.vndErrors;

@ConditionalOnRestEnabled
@RestController
public class ErrorRestController implements ErrorController {

    public final static String PATH = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;

    @LogExecutionTime
    @GetMapping(value = PATH, produces = APPLICATION_VND_ERROR_JSON_VALUE)
    public ResponseEntity<VndErrors> error(HttpServletRequest request) {

        final int errorStatus = getErrorStatus(request);
        final Map<String, String> errors = convertErrorAttributes(request);

        return ResponseEntity.status(errorStatus).body(vndErrors(errors));
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

    private Map<String, Object> errorAttributes(final HttpServletRequest request) {
        final RequestAttributes requestAttributes = new ServletRequestAttributes(request);

        return errorAttributes.getErrorAttributes(requestAttributes, false);
    }

    private Map<String, String> convertErrorAttributes(final HttpServletRequest request) {
        return errorAttributes(request).entrySet().stream()
                                       .collect(toMap(Map.Entry::getKey,
                                                      entry -> entry.getValue().toString()));
    }

    private int getErrorStatus(final HttpServletRequest request) {
        final Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        return statusCode != null ? statusCode : HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
