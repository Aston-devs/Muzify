CREATE SCHEMA IF NOT EXISTS music_service;

CREATE
EXTENSION "uuid-ossp";

CREATE TABLE cover
(
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    url        VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE image
(
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    url        VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE author
(
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    name       VARCHAR(100) NOT NULL UNIQUE,
    photo_id UUID UNIQUE,
    genre      VARCHAR,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    FOREIGN KEY (photo_id) REFERENCES image (id)
);

CREATE TABLE song
(
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    title        VARCHAR(150) NOT NULL,
    genre        VARCHAR(100),
    release_date TIMESTAMP WITHOUT TIME ZONE,
    author_id UUID,
    cover_id UUID UNIQUE,
    url          VARCHAR      NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE,
    updated_at   TIMESTAMP WITHOUT TIME ZONE,
    FOREIGN KEY (author_id) REFERENCES author (id),
    FOREIGN KEY (cover_id) REFERENCES cover (id)
);

CREATE TABLE app_user
(
    id UUID PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE user_songs
(
    user_id UUID,
    song_id UUID,
    PRIMARY KEY (user_id, song_id),
    FOREIGN KEY (user_id) REFERENCES app_user (id),
    FOREIGN KEY (song_id) REFERENCES song (id)
);