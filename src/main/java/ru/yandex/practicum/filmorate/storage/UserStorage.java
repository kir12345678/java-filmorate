package ru.yandex.practicum.filmorate.storage;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

public class UserStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private int userId = 1;

    public User create(User user) {
        user.setId(userId++);
        users.put(user.getId(), user);
        return user;
    }

    public User update(User user) throws NotFoundException {
        int id = user.getId();
        if (!users.containsKey(id)) {
            throw new NotFoundException("Film with id " + id + " not found");
        }
        users.put(id, user);
        return user;
    }

    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

}