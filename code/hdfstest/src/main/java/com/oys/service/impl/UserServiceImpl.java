package com.oys.service.impl;

import com.oys.dao.UserDao;
import com.oys.dao.impl.UserDaoImpl;
import com.oys.pojo.User;
import com.oys.service.UserService;

public class UserServiceImpl implements UserService {

    UserDao userDao = new UserDaoImpl();

    @Override
    public User findUser(String username) {
        return userDao.findUser(username);
    }

    @Override
    public boolean addUser(String username, String psw) {
        return userDao.addUser(username, psw);
    }
}
