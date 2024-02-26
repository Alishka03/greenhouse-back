package kz.iitu.smartgreenhouse.web.rest.errors;

import java.util.Map;

public abstract class ApiException extends RuntimeException {

    private Map<String, Object> fieldErrors;

    public ApiException() {}

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Map<String, Object> fieldErrors) {
        super(message);
        this.fieldErrors = fieldErrors;
    }

    public abstract ApiErrors getApiError();

    public Map<String, Object> getFieldErrors() {
        return fieldErrors;
    }
}
