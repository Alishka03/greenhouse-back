package kz.iitu.auth.errors;

import java.util.Map;

public class ValidationError extends ApiException {

    ApiErrors apiError = ApiErrors.ValidationError;

    private Map<String, Object> fieldErrors;

    public ValidationError() {}

    public ValidationError(String message) {
        super(message);
    }

    public ValidationError(Map<String, Object> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public ValidationError(String message, Map<String, Object> fieldErrors) {
        super(message);
        this.fieldErrors = fieldErrors;
    }

    @Override
    public ApiErrors getApiError() {
        return apiError;
    }

    @Override
    public Map<String, Object> getFieldErrors() {
        return fieldErrors;
    }
}
