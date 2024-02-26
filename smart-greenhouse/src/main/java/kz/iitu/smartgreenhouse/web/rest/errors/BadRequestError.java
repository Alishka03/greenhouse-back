package kz.iitu.smartgreenhouse.web.rest.errors;

public class BadRequestError extends ApiException {

    ApiErrors apiError = ApiErrors.BadRequest;

    public BadRequestError() {}

    public BadRequestError(String message) {
        super(message);
    }

    @Override
    public ApiErrors getApiError() {
        return apiError;
    }
}
