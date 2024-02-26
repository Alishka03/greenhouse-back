package kz.iitu.auth.errors;

public class UnauthorizedError extends ApiException {

    ApiErrors apiError = ApiErrors.Unauthorized;

    public UnauthorizedError() {}

    public UnauthorizedError(String message) {
        super(message);
    }

    @Override
    public ApiErrors getApiError() {
        return apiError;
    }
}
