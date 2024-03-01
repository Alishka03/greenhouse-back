package kz.iitu.smartgreenhouse.config;

import org.springframework.http.MediaType;

import java.time.ZoneId;

public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";
    public static final String PHONE_REGEX = "^[0-9]{10}$";
    public static final String PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*\\d).{6,32}$";
    public static final String VALID_FULL_PHONE_NUMBER = "Телефон нөмірін толық толтырыңыз";
    public static final String VALID_PHONE_NUMBER = "Телефон нөмірін толтырыңыз";
    public static final String SYSTEM = "system";
    public static final String DEFAULT_LANGUAGE = "en";
    public static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final ZoneId ALMATY_ZONE_ID = ZoneId.of("Asia/Almaty");
    public static final MediaType EXCEL_MEDIA_TYPE = MediaType.valueOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    private Constants() {}
}
