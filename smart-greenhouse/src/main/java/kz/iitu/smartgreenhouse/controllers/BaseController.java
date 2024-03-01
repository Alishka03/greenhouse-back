package kz.iitu.smartgreenhouse.controllers;


import jakarta.ws.rs.NotFoundException;
import kz.iitu.smartgreenhouse.model.dto.ErrorDto;
import kz.iitu.smartgreenhouse.web.rest.errors.*;
import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class BaseController {

    private final Logger log = LoggerFactory.getLogger(BaseController.class);

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDto> errorApiException(ApiException exception) {
        log.error(exception.getMessage(), exception);

        ErrorDto errorDto = new ErrorDto();
        errorDto.setError(exception.getApiError().getCode());
        errorDto.setDetail(exception.getMessage() != null ? exception.getMessage() : exception.getApiError().getDefaultDescription());
        errorDto.setStatus(exception.getApiError().getStatus());
        errorDto.setFieldErrors(exception.getFieldErrors());
        return ResponseEntity.status(exception.getApiError().getStatus()).body(errorDto);
    }

    @ExceptionHandler(ForbiddenError.class)
    public ResponseEntity<ErrorDto> errorApiException(ForbiddenError exception) {
        log.error(exception.getMessage(), exception);

        ErrorDto errorDto = new ErrorDto();
        errorDto.setError(ApiErrors.Forbidden.getCode());
        errorDto.setDetail(exception.getMessage());
        errorDto.setStatus(ApiErrors.Forbidden.getStatus());
        return ResponseEntity.status(ApiErrors.Forbidden.getStatus()).body(errorDto);
    }

    @ExceptionHandler({NotFoundException.class, ObjectNotFoundException.class})
    public ResponseEntity<ErrorDto> errorApiException(Exception exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError(ApiErrors.ObjectNotFound.getCode());
        errorDto.setDetail(exception.getMessage());
        errorDto.setStatus(ApiErrors.ObjectNotFound.getStatus());
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(ApiErrors.ObjectNotFound.getStatus()).body(errorDto);
    }

    @ExceptionHandler(UnauthorizedError.class)
    public ResponseEntity<ErrorDto> errorApiException(UnauthorizedError exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError(ApiErrors.Unauthorized.getCode());
        errorDto.setDetail(exception.getMessage());
        errorDto.setStatus(ApiErrors.Unauthorized.getStatus());
        return ResponseEntity.status(ApiErrors.Unauthorized.getStatus()).body(errorDto);
    }

    @ExceptionHandler(ErrorDtoException.class)
    public ResponseEntity<ErrorDto> errorDtoResponseEntity(ErrorDtoException exception) {
        int status = exception.getErrorDto().getStatus();
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(status != 0 ? status : 500).body(exception.getErrorDto());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDto> errorDtoResponseEntity(RuntimeException exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError(exception.getMessage());
        errorDto.setDetail(exception.getCause().getMessage() != null ? exception.getCause().getMessage() : "INTERNAL SERVER ERROR");
        errorDto.setStatus(500);
        return ResponseEntity.status(errorDto.getStatus()).body(errorDto);
    }
}
