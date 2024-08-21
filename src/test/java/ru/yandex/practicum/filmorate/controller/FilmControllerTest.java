package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmControllerTest {
    private final FilmController filmController = new FilmController();

    @Test
    void testCreate() {

        Film film = new Film();
        film.setName("FilmName");
        film.setDescription("My Film Description");
        film.setDuration(Duration.ofMinutes(90));
        film.setReleaseDate(LocalDate.parse("21.08.2024", DateTimeFormatter.ofPattern("dd.MM.yyyy")));

        Film result = filmController.create(film);
        assertEquals(1, result.getId(), "IDs don't equal");

    }
}
