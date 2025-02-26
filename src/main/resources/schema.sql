DROP TABLE IF EXISTS FILM_GENRE;
DROP TABLE IF EXISTS "LIKE";
DROP TABLE IF EXISTS FRIENDSHIP;
DROP TABLE IF EXISTS GENRE;
DROP TABLE IF EXISTS FILM;
DROP TABLE IF EXISTS "USER";
DROP TABLE IF EXISTS MPA;

CREATE TABLE mpa (
    id INTEGER PRIMARY KEY,
    name VARCHAR
);

CREATE TABLE film (
    id INTEGER PRIMARY KEY,
    name VARCHAR,
    description VARCHAR,
    release_date DATE,
    duration INTEGER,
    mpa_id INTEGER,
    FOREIGN KEY (mpa_id) REFERENCES mpa(id)
);

CREATE TABLE genre (
    id INTEGER PRIMARY KEY,
    desc VARCHAR
);

CREATE TABLE "USER" (
    id INTEGER PRIMARY KEY,
    birthday DATE,
    email VARCHAR NOT NULL,
    login VARCHAR
);

CREATE TABLE "LIKE" (
    id INTEGER PRIMARY KEY,
    film_id INTEGER,
    user_id INTEGER,
    FOREIGN KEY (film_id) REFERENCES film(id),
    FOREIGN KEY (user_id) REFERENCES "USER"(id)
);

CREATE TABLE film_genre (
    id INTEGER PRIMARY KEY,
    film_id INTEGER,
    genre_id INTEGER,
    FOREIGN KEY (film_id) REFERENCES film(id),
    FOREIGN KEY (genre_id) REFERENCES genre(id)
);

CREATE TABLE friendship (
    id INTEGER PRIMARY KEY,
    user_id INTEGER,
    friend_id INTEGER,
    accepted BOOLEAN,
    FOREIGN KEY (user_id) REFERENCES "USER"(id),
    FOREIGN KEY (friend_id) REFERENCES "USER"(id)
);

ALTER TABLE film
ADD CONSTRAINT fk_film_mpa
FOREIGN KEY (mpa_id) REFERENCES mpa(id);