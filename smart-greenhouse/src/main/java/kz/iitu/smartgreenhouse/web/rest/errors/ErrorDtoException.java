package kz.iitu.smartgreenhouse.web.rest.errors;


import kz.iitu.smartgreenhouse.model.dto.ErrorDto;

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
