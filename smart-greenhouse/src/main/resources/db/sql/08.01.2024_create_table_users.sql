CREATE TABLE users
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    first_name      VARCHAR(255),
    last_name       VARCHAR(255),
    email           VARCHAR(255),
    password        VARCHAR(255),
    date_of_birth   date,
    profile_picture VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (id)
);