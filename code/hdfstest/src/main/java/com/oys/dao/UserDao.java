package com.oys.dao;

import com.oys.pojo.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserDao {

	User findUser(String username);

	boolean addUser(String username, String psw);
}
