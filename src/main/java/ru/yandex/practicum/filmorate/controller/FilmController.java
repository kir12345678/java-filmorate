package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    FilmStorage filmStorage = new FilmStorage();

    @PostMapping
    public Film create(@RequestBody  @Valid Film film) {
        log.info("New film created: {}", film);
        return filmStorage.create(film);
    }

    @PutMapping("/{id}")
    public Film update(@PathVariable int id, @RequestBody @Valid Film film) throws NotFoundException {
        log.info("Film updated: {}", film);
        return filmStorage.update(id, film);
    }

    @GetMapping
    public Collection<Film> getAll() {
        log.info("All films");
        return filmStorage.getAll();
    }
}
