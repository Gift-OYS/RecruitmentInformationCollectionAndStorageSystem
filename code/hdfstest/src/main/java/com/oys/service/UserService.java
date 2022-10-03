package com.oys.service;

import com.oys.pojo.User;

public interface UserService {

    User findUser(String username);

    boolean addUser(String username, String psw);
}
