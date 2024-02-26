package kz.iitu.auth.errors;

public enum ApiErrors {
    BadRequest("bad_request", 400, "Bad Request"),
    InvalidLoginOrPassword("invalid_login_or_password", 400, "Пошта немесе құпия сөз дұрыс емес!"),
    InvalidIinOrPassword("invalid_login_or_password", 401, "ЖСН немесе құпия сөз дұрыс емес!"),
    ValidationError("validation_error", 400, "Тексеруден өтпеді"),
    Unauthorized("unauthorized", 401, "Unauthorized"),
    Forbidden("forbidden", 403, "Сізде рұқсат жок"),
    ObjectNotFound("object_not_found", 404, "Табылған жоқ"),
    UserEntityNotFound("user_not_found_error", 404, "Пайдаланушы табылмады"),
    UserBlockedError("blocked_user", 401, "Пайдаланушы бұғатталған. Жүйе әкімшілігіне хабарласыңыз"),
    EmptyFileError("empty_file", 400, "Жүктеуге арналған файлды таңдаңыз");

    ApiErrors(String code, int status, String defaultDescription) {
        this.code = code;
        this.status = status;
        this.defaultDescription = defaultDescription;
    }

    String code;

    int status;

    String defaultDescription;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDefaultDescription() {
        return defaultDescription;
    }

    public void setDefaultDescription(String defaultDescription) {
        this.defaultDescription = defaultDescription;
    }
}
