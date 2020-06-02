CREATE TABLE IF NOT EXISTS profile (
    id serial PRIMARY KEY,
    phone_number VARCHAR(255),
    lang VARCHAR(50),
    country VARCHAR(50),
    time_zone VARCHAR(100)
);
insert into profile VALUES ( 1, '6715275', 'en', '', 'Australia/Brisbane' );
