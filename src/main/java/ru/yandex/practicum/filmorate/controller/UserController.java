package ru.yandex.practicum.filmorate.controller;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;


import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

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
    public User create(@Valid @RequestBody User user) {
        validateUser(user);
        log.info("New user created: {}", user);
        return userService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) throws NotFoundException {
        validateUser(user);
        log.info("User updated: {}", user);
        return userService.update(user);
    }

    @GetMapping
    public List<User> getAll() {
        List<User> users = userService.getAll();
        if (users.isEmpty()) {
            log.info("Not users");
            throw new ValidationException("Not Users");
        }
        log.info("All users");
        return users;
    }

    @PutMapping(value = "/{id}/friends/{friendId}")
    public User addFriend(@PathVariable Integer id, @PathVariable Integer friendId) throws NotFoundException {
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping(value = "/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable Integer id, @PathVariable Integer friendId) throws NotFoundException {
        return userService.deleteFriend(id, friendId);
    }

    @GetMapping(value = "/{id}/friends")
    public List<User> getFriend(@PathVariable Integer id) throws NotFoundException {
        return userService.findFriends(id);
    }

    @GetMapping(value = "/{id}/friends/common/{otherId}")
    public List<User> getCommonFriend(@PathVariable Integer id, @PathVariable Integer otherId) throws NotFoundException {
        return userService.findCommonFriends(id, otherId);
    }
}

