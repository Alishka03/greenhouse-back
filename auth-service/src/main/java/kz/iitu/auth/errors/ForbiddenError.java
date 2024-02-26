package kz.iitu.auth.errors;

public class ForbiddenError extends ApiException {

    ApiErrors apiError = ApiErrors.Forbidden;

    public ForbiddenError() {
        super(ApiErrors.Forbidden.getDefaultDescription());
    }

    public ForbiddenError(String message) {
        super(message);
    }

    @Override
    public ApiErrors getApiError() {
        return apiError;
    }
}
