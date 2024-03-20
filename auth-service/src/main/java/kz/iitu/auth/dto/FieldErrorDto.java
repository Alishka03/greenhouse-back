package kz.iitu.auth.dto;

public class FieldErrorDto {

    private String fieldName;

    private String message;

    public FieldErrorDto(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return "FieldErrorDto{" + "fieldName='" + fieldName + '\'' + ", message='" + message + '\'' + '}';
    }
}
