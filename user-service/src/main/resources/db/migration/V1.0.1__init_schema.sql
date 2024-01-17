CREATE SCHEMA IF NOT EXISTS user_service;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users(
    id       uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    role VARCHAR(100) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);