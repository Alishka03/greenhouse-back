package kz.iitu.smartgreenhouse.web.rest.errors;

public class ObjectNotFoundError extends ApiException {

    ApiErrors apiError = ApiErrors.ObjectNotFound;

    public ObjectNotFoundError() {
        super(ApiErrors.ObjectNotFound.getDefaultDescription());
    }

    public ObjectNotFoundError(String message) {
        super(message);
    }

    @Override
    public ApiErrors getApiError() {
        return apiError;
    }
}
