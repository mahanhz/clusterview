package org.amhzing.clusterview.app.web.model.error;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public final class ErrorResponse {

    private final String errorId;
    private final String status;
    private final String path;
    private final String message;

    private ErrorResponse(final String errorId, final String status, final String path, final String message) {
        this.errorId = notNull(errorId);
        this.status = notNull(status);
        this.path = notNull(path);
        this.message = notNull(message);
    }

    public static ErrorResponse create(final String errorId, final String status, final String path, final String message) {
        return new ErrorResponse(errorId, status, path, message);
    }

    public String getErrorId() {
        return errorId;
    }

    public String getStatus() {
        return status;
    }

    public String getPath() {
        return path;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ErrorResponse)) return false;
        final ErrorResponse that = (ErrorResponse) o;
        return Objects.equals(errorId, that.errorId) &&
                Objects.equals(status, that.status) &&
                Objects.equals(path, that.path) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorId, status, path, message);
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "errorId='" + errorId + '\'' +
                ", status='" + status + '\'' +
                ", path='" + path + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
