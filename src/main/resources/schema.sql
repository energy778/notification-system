CREATE TABLE IF NOT EXISTS profile (
    id identity PRIMARY KEY,
    phone_number VARCHAR(255),
    lang VARCHAR(50),
    country VARCHAR(50),
    time_zone VARCHAR(100)
);