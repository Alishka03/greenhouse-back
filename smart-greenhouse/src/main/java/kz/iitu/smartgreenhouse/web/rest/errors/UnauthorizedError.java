package kz.iitu.smartgreenhouse.web.rest.errors;

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
