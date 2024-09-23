package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) throws NotFoundException {
        return filmStorage.update(film);
    }

    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    public Film find(Integer id) throws NotFoundException {
        return filmStorage.find(id);
    }

    public Film addLike(Integer filmId, Integer userId) throws NotFoundException {
        Film film = filmStorage.find(filmId);
        User user = userStorage.find(userId);
        Set<Integer> filmLikesId = film.getLikes();
        filmLikesId.add(userId);
        film.setLikes(filmLikesId);
        return filmStorage.update(film);
    }

    public Film deleteLike(Integer filmId, Integer userId) throws NotFoundException {
        Film film = filmStorage.find(filmId);
        User user = userStorage.find(userId);
        Set<Integer> filmLikesId = film.getLikes();
        filmLikesId.remove(userId);
        film.setLikes(filmLikesId);
        return film;
    }

    public List<Film> findPopFilms(Integer size) {
        return filmStorage.getAll().stream().sorted(this::compare).limit(size).collect(Collectors.toList());
    }

    private int compare(Film p0, Film p1) {
        return p1.getLikes().size() - p0.getLikes().size();
    }


}
