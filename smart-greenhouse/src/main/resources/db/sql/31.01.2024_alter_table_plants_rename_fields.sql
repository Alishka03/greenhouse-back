ALTER TABLE breakpoints
RENAME COLUMN temperature TO const_temperature;
ALTER TABLE breakpoints
RENAME COLUMN humidity TO const_humidity;
ALTER TABLE breakpoints
RENAME COLUMN carbon_dioxide TO const_carbon_dioxide;
