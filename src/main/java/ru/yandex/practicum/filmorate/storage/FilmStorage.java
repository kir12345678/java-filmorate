package ru.yandex.practicum.filmorate.storage;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;


public interface FilmStorage {

    Film create(Film film);

    Film update(Film film) throws NotFoundException;

    List<Film> getAll();

    Film find(Integer id) throws NotFoundException;

    boolean delete(Integer id);

}
