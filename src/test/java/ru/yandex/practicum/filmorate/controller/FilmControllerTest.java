package ru.yandex.practicum.filmorate.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;



import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmControllerTest {

    private FilmController filmController;

    @BeforeEach
    public void setUp() {
        InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();
        InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
        FilmService filmService = new FilmService(inMemoryFilmStorage, inMemoryUserStorage);
        filmController = new FilmController(filmService);
    }

    @Test
    void createFilm() {

        Film film = new Film();
        film.setName("FilmName");
        film.setDescription("My Film Description");
        film.setDuration(90);
        film.setReleaseDate(LocalDate.parse("21.08.2024", DateTimeFormatter.ofPattern("dd.MM.yyyy")));

        Film result = filmController.create(film);
        assertEquals(1, result.getId(), "IDs don't equal");

    }

}
