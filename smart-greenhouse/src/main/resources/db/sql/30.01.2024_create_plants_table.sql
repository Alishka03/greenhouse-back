-- Create plants table
CREATE TABLE plants
(
    id bigserial PRIMARY KEY NOT NULL,
    name varchar(255),
    description varchar,
    image_url varchar,
    const_temperature double precision,
    const_humidity double precision,
    const_carbon_dioxide double precision
);

-- Insert values into plants table
INSERT INTO plants (name, description, image_url, const_temperature, const_humidity, const_carbon_dioxide)
VALUES
    ('Basil', 'Fragrant herb used in cooking.', '65e1bc44a78f8d5755253076', 75.0, 55.0, 400.0),
    ('Spider Plant', 'Popular houseplant with arching leaves.', '65e1bca4a78f8d575525307c', 70.0, 50.0, 350.0),
    ('Orchid', 'Elegant flowering plant with various colors.', '65e1bc97a78f8d575525307a', 72.0, 60.0, 380.0),
    ('Aloe Vera', 'Succulent plant with soothing gel in leaves.', '65e1bc32a78f8d5755253074', 68.0, 35.0, 300.0),
    ('Peace Lily', 'Plant with glossy leaves and white flowers.', '65e1bc53a78f8d5755253078', 75.0, 45.0, 380.0);


DROP TABLE IF EXISTS breakpoints;
