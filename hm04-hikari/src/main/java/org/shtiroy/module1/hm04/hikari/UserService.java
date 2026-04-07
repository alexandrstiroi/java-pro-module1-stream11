package org.shtiroy.module1.hm04.hikari;

import java.util.List;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void createUser(String username) {
        userDao.create(new User(username));
    }

    public User getUser(Long id) {
        return userDao.getById(id);
    }

    public List<User> getAllUsers() {
        return userDao.getAll();
    }

    public void deleteUser(Long id) {
        userDao.delete(id);
    }
}
