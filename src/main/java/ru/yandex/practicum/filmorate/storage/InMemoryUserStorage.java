package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int userId = 1;

    @Override
    public User create(User user) {
        user.setId(userId++);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) throws NotFoundException {
        int id = user.getId();
        if (!users.containsKey(id)) {
            throw new NotFoundException("Film with id " + id + " not found");
        }
        users.put(id, user);
        return user;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User find(Integer id) throws NotFoundException {
        if (users.containsKey(id)) {
            return users.get(id);
        } else {
            log.info("Пользователь не найден с ID - {}", id);
            throw new NotFoundException(String.format("Пользователя с id-\"%d\" не существует.", id));
        }
    }
}
