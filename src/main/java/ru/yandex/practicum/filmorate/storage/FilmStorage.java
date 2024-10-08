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

    public Film update(Film film) throws NotFoundException {
        int id = film.getId();
        if (!films.containsKey(id)) {
            throw new NotFoundException("Film with id " + id + " not found");
        }
        films.put(id, film);
        return film;
    }

    public List<Film> getAll() {
       return new ArrayList<>(films.values());
    }

}
