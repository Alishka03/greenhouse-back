create table plants
(
    id   bigserial primary key not null,
    name    varchar(255),
    description varchar,
    breakpoint_id bigserial references breakpoints(id)
);
