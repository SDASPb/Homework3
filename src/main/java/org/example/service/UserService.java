package org.example.service;

import org.example.dao.UserDao;
import org.example.model.User;

import java.sql.SQLException;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void register(String name) throws SQLException {
        User user = new User(null, name);
        userDao.save(user);
    }
}
