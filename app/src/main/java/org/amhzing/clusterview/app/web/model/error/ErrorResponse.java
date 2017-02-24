package org.amhzing.clusterview.app.web.model.error;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public final class ErrorResponse {

    public static final String STATUS = "status";
    public static final String ERROR = "error";
    public static final String PATH = "path";
    public static final String MESSAGE = "message";
    public static final String TIMESTAMP = "timestamp";

    private final String errorId;
    public final Integer status;
    public final String error;
    public final String path;
    public final String message;
    public final String timeStamp;

    private ErrorResponse(final String errorId, final Map<String, Object> errorAttributes) {
        this.errorId = notNull(errorId);
        this.status = (Integer) errorAttributes.getOrDefault(STATUS, -1);
        this.error = (String) errorAttributes.getOrDefault(ERROR, "");
        this.path = (String) errorAttributes.getOrDefault(PATH, "");
        this.message = (String) errorAttributes.getOrDefault(MESSAGE, "");
        this.timeStamp = errorAttributes.getOrDefault(TIMESTAMP, LocalDateTime.now()).toString();
    }

    public static ErrorResponse create(final String errorId, Map<String, Object> errorAttributes) {
        return new ErrorResponse(errorId, errorAttributes);
    }

    public String getErrorId() {
        return errorId;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getPath() {
        return path;
    }

    public String getMessage() {
        return message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ErrorResponse)) return false;
        final ErrorResponse that = (ErrorResponse) o;
        return Objects.equals(errorId, that.errorId) &&
                Objects.equals(status, that.status) &&
                Objects.equals(error, that.error) &&
                Objects.equals(path, that.path) &&
                Objects.equals(message, that.message) &&
                Objects.equals(timeStamp, that.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorId, status, error, path, message, timeStamp);
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "errorId='" + errorId + '\'' +
                ", status=" + status +
                ", error='" + error + '\'' +
                ", path='" + path + '\'' +
                ", message='" + message + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                '}';
    }
}
