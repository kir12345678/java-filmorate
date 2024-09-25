package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int filmId = 1;

    @Override
    public Film create(Film film) {
        film.setId(filmId++);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) throws NotFoundException {
        int id = film.getId();
        if (!films.containsKey(id)) {
            throw new NotFoundException("Film with id " + id + " not found");
        }
        films.put(id, film);
        return film;
    }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film find(Integer id) throws NotFoundException {
        if (films.containsKey(id)) {
            return films.get(id);
        } else {
            log.info("фильм не найден с ID - {}", id);
            throw new NotFoundException(String.format("Фильма с id-\"%d\" не существует.", id));
        }
    }

    @Override
    public boolean delete(Integer id) {
        if (films.containsKey(id)) {
            films.remove(id);
            return true;
        } else {
            return false;
        }
    }
}
