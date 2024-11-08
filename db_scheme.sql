CREATE TABLE "film" (
  "id" integer PRIMARY KEY,
  "name" varchar,
  "description" varchar,
  "release_date" date,
  "duration" integer,
  "MPA_id" integer
);

CREATE TABLE "genre" (
  "id" integer PRIMARY KEY,
  "desc" varchar
);

CREATE TABLE "motion_picture_association" (
  "id" integer PRIMARY KEY,
  "desc" varchar
);

CREATE TABLE "like" (
  "id" integer PRIMARY KEY,
  "film_id" integer,
  "user_id" integer
);

CREATE TABLE "user" (
  "id" integer PRIMARY KEY,
  "birthday" date,
  "email" varchar,
  "login" varchar
);

CREATE TABLE "film_genre" (
  "id" integer PRIMARY KEY,
  "film_id" integer,
  "genre_id" integer
);

CREATE TABLE "friendship" (
  "id" integer PRIMARY KEY,
  "user_id" integer,
  "friend_id" integer,
  "accepted" boolean
);

ALTER TABLE "like" ADD FOREIGN KEY ("film_id") REFERENCES "film" ("id");

ALTER TABLE "like" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");

ALTER TABLE "film" ADD FOREIGN KEY ("id") REFERENCES "motion_picture_association" ("id");

ALTER TABLE "film_genre" ADD FOREIGN KEY ("genre_id") REFERENCES "genre" ("id");

ALTER TABLE "film_genre" ADD FOREIGN KEY ("id") REFERENCES "film" ("id");

ALTER TABLE "friendship" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");

ALTER TABLE "friendship" ADD FOREIGN KEY ("friend_id") REFERENCES "user" ("id");
