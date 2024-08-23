package ru.yandex.practicum.filmorate.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    UserStorage userStorage = new UserStorage();

    private void validateUser(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и не может содержать пробелы");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin()); // Имя по умолчанию будет логин, если не указано
        }
        if (user.getBirthday() != null && user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }

    }

    @PostMapping
    public User create(@RequestBody User user) {
        validateUser(user);
        log.info("New user created: {}", user);
        return userStorage.create(user);
    }

    @PutMapping
    public User update(@RequestBody User user) throws NotFoundException {
        validateUser(user);
        log.info("User updated: {}", user);
        return userStorage.update(user);
    }

    @GetMapping
    public List<User> getAll() {
        List<User> users = userStorage.getAll();
        if (users.isEmpty()) {
            log.info("Nor users");
            throw new ValidationException("Not Users");
        }
        log.info("All users");
        return users;
    }
}

