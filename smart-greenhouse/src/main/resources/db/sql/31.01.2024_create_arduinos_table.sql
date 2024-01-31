create table arduinos
(
    id   bigserial primary key not null,
    temperature    float,
    humidity       float,
    carbon_dioxide float,
    plant_id bigserial references plants(id)
);

