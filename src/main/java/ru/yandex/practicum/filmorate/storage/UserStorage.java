package ru.yandex.practicum.filmorate.storage;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

public interface UserStorage {
    User create(User user);

    User update(User user) throws NotFoundException;

    List<User> getAll();

    User find(Integer id) throws NotFoundException;
}