create table greenhouses
(
    id   bigserial primary key not null,
    name varchar(255),
    owner_id bigint references users(id),
    arduino_id bigserial references arduinos(id)
);

