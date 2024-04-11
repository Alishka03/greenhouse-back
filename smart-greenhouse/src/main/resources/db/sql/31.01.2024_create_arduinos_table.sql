create table arduinos
(
    id  bigserial primary key not null,
    temperature    float,
    humidity_air       float,
    humidity_ground       float,
    carbon_dioxide float,
    plant_id bigserial references plants(id)
);

