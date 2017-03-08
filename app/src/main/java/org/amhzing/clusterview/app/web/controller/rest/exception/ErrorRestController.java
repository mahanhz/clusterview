package org.amhzing.clusterview.app.web.controller.rest.exception;

import org.amhzing.clusterview.app.web.model.error.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@ConditionalOnProperty(prefix = "clusterview.rest", name = "error-controller")
public class ErrorRestController implements ErrorController {

    public final static String PATH = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;

    @GetMapping(value = PATH)
    public ErrorResponse error(HttpServletRequest request) {
        return ErrorResponse.create("", errorAttributes(request));
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

    private Map<String, Object> errorAttributes(final HttpServletRequest request) {
        final RequestAttributes requestAttributes = new ServletRequestAttributes(request);

        return errorAttributes.getErrorAttributes(requestAttributes, false);
    }
}
