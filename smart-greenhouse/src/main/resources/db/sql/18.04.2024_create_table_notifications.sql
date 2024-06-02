create table notifications
(
    id   bigserial primary key not null,
    arduino_id  bigserial references arduinos(id),
    optimal_temperature boolean,
    optimal_humidity_air boolean,
    optimal_humidity_ground boolean,
    optimal_light boolean,
    optimal_carbon_dioxide boolean
);