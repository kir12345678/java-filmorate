package ru.yandex.practicum.filmorate.controller;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    UserStorage userStorage=new UserStorage();

    @PostMapping
    public User create(@RequestBody @Valid User user) {
        log.info("New user created: {}", user);
        return userStorage.create(user);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable int id, @RequestBody @Valid User user) throws NotFoundException {
        log.info("User updated: {}", user);
        return userStorage.update(id, user);
    }

    @GetMapping
    public Collection<User> getAll() {
        log.info("All users");
        return userStorage.getAll();
    }
}
