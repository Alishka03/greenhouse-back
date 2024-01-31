create table breakpoints
(
    id   bigserial primary key not null,
    temperature    float,
    humidity       float,
    carbon_dioxide float
);

