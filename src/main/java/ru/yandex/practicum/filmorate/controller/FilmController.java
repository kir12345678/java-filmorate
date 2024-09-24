package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    private void validateFilm(Film film) {
        if (film.getName() == null || film.getName().isEmpty()) {
            throw new ValidationException("Название фильма не может быть пустым");
        }
        if (film.getDescription() != null && film.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate() != null && film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительной!");
        }
    }

    @PostMapping
    public Film create(@Valid @RequestBody  Film film) {
        log.info("New film created: {}", film);
        validateFilm(film);
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@RequestBody Film film) throws NotFoundException {
        log.info("Film updated: {}", film);
        validateFilm(film);
        return filmService.update(film);
    }

    @GetMapping
    public List<Film> getAll() throws NotFoundException {
        List<Film> films = filmService.getAll();
        if (films.isEmpty()) {
            log.warn("Запрос на получение всех фильмов, но список фильмов пуст.");
            throw new NotFoundException("Нет доступных фильмов.");
        }
        return films;
    }

    @GetMapping("/{id}")
    public Film find(@PathVariable Integer id) throws NotFoundException {
        return filmService.find(id);
    }

    @PutMapping(value = "/{id}/like/{userId}")
    public Film addLike(@PathVariable Integer id, @PathVariable Integer userId) throws NotFoundException {
        return filmService.addLike(id, userId);
    }

    @DeleteMapping(value = "/{id}/like/{userId}")
    public Film deleteLike(@PathVariable Integer id, @PathVariable Integer userId) throws NotFoundException {
        return filmService.deleteLike(id, userId);
    }

    @GetMapping(value = "/popular")
    public List<Film> deleteLike(@RequestParam(defaultValue = "10") int count) {
        return filmService.findPopFilms(count);
    }
}
