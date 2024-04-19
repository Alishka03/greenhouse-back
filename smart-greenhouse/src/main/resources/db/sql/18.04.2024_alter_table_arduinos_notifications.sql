ALTER TABLE arduinos
    ADD COLUMN notification_id BIGINT REFERENCES notifications(id);
