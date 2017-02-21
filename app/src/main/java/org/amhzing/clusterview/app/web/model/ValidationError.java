package org.amhzing.clusterview.app.web.model;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public class ValidationError {

    private String errorMessage;
    private List<String> errors;

    private ValidationError(final String errorMessage, final List<String> errors) {
        this.errorMessage = notNull(errorMessage);
        this.errors = notNull(errors);
    }

    public static ValidationError create(final String errorMessage) {
        return new ValidationError(errorMessage, ImmutableList.of());
    }

    public static ValidationError create(final String errorMessage, final List<String> errors) {
        return new ValidationError(errorMessage, errors);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<String> getErrors() {
        return ImmutableList.copyOf(errors);
    }

    public void setErrors(final List<String> errors) {
        this.errors = errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ValidationError)) return false;
        final ValidationError that = (ValidationError) o;
        return Objects.equals(errorMessage, that.errorMessage) &&
                Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorMessage, errors);
    }

    @Override
    public String toString() {
        return "ValidationError{" +
                "errorMessage='" + errorMessage + '\'' +
                ", errors=" + errors +
                '}';
    }
}
