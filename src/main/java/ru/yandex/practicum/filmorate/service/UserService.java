package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

import ru.yandex.practicum.filmorate.model.User;

import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserService {
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User create(User user) {
        return userStorage.create(user);
    }

    public User update(User user) throws NotFoundException {
        return userStorage.update(user);
    }

    public List<User> getAll() {
        return userStorage.getAll();
    }

    public User find(Integer id) throws NotFoundException {
        return userStorage.find(id);
    }

    public User addFriend(Integer id, Integer friendId) throws NotFoundException {
        User user = userStorage.find(id);
        User friend = userStorage.find(friendId);

        user.addFriend(friendId);
        friend.addFriend(id);
        return userStorage.update(user);
    }

    public User deleteFriend(Integer id, Integer friendId) throws NotFoundException {
        User user = userStorage.find(id);
        User friend = userStorage.find(friendId);

        user.deleteFriend(friendId);
        friend.deleteFriend(id);
        return userStorage.update(user);
    }

    public List<User> findFriends(Integer id) throws NotFoundException {
        User user = userStorage.find(id);
        Set<Integer> userFriendsId = user.getFriends();
        ArrayList<User> userFriends = new ArrayList<>();
        for (Integer friendId : userFriendsId) {
            userFriends.add(userStorage.find(friendId));
        }
        return userFriends;
    }

    public List<User> findCommonFriends(Integer id, Integer friendId) throws NotFoundException {
        User user = userStorage.find(id);
        User friend = userStorage.find(friendId);

        Set<Integer> commonFriendsId = new HashSet<>(user.getFriends());
        commonFriendsId.retainAll(friend.getFriends());

        ArrayList<User> userFriends = new ArrayList<>();
        for (Integer userId : commonFriendsId) {
            userFriends.add(userStorage.find(userId));
        }
        return userFriends;
   }
}
