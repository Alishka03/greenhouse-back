package kz.iitu.auth.errors;


import kz.iitu.auth.dto.ErrorDto;

public class ErrorDtoException extends RuntimeException {

    private ErrorDto errorDto;

    public ErrorDtoException(ErrorDto errorDto) {
        this.errorDto = errorDto;
    }

    public ErrorDto getErrorDto() {
        return errorDto;
    }

    public void setErrorDto(ErrorDto errorDto) {
        this.errorDto = errorDto;
    }
}
