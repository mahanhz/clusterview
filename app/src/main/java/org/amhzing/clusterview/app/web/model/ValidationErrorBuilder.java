package org.amhzing.clusterview.app.web.model;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.Errors;

import java.util.List;

import static java.util.stream.Collectors.toList;

public final class ValidationErrorBuilder {

    private ValidationErrorBuilder() {
        // Prevent instantiation
    }

    public static ValidationError fromBindingErrors(Errors errors) {
        final String errorMessage = "Validation failed. " + errors.getErrorCount() + " error(s)";

        final List<String> collectedErrors = errors.getAllErrors()
                                                   .stream()
                                                   .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                                   .collect(toList());

        return ValidationError.create(errorMessage, collectedErrors);
    }
}
