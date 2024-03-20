package kz.iitu.auth.errors;

public class UserNotFoundError extends ApiException {

    ApiErrors apiError = ApiErrors.UserEntityNotFound;

    public UserNotFoundError() {
        super(ApiErrors.UserEntityNotFound.getDefaultDescription());
    }

    public UserNotFoundError(String message) {
        super(message);
    }

    @Override
    public ApiErrors getApiError() {
        return apiError;
    }
}
