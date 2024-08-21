package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

public class FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int filmId = 1;

    public Film create(Film film) {
        film.setId(filmId++);
        films.put(film.getId(), film);
        return film;
    }

    public Film update(int id, Film film) throws NotFoundException {
        if (!films.containsKey(id)) {
            throw new NotFoundException("Film with id " + id + " not found");
        }
        film.setId(id);
        films.put(id, film);
        return film;
    }

    public Collection<Film> getAll() {
        return films.values();
    }
}
