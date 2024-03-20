package kz.iitu.auth.errors;

public class ValidationResult<T> {

    T body;
    boolean hasError;

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }
}
