DROP TABLE IF EXISTS film_genre;
DROP TABLE IF EXISTS likes;
DROP TABLE IF EXISTS friendship;
DROP TABLE IF EXISTS genre;
DROP TABLE IF EXISTS film;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS mpa;

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
    description VARCHAR
);

CREATE TABLE users (
    id INTEGER PRIMARY KEY,
    birthday DATE,
    email VARCHAR NOT NULL,
    login VARCHAR NOT NULL
);

CREATE TABLE likes (
    id INTEGER PRIMARY KEY,
    film_id INTEGER,
    user_id INTEGER,
    FOREIGN KEY (film_id) REFERENCES film(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
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
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (friend_id) REFERENCES users(id)
);

ALTER TABLE film
ADD CONSTRAINT fk_film_mpa
FOREIGN KEY (mpa_id) REFERENCES mpa(id);