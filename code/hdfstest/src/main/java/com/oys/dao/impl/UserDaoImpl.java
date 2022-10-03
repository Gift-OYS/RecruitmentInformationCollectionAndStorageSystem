package com.oys.dao.impl;

import com.oys.dao.UserDao;
import com.oys.db.BaseDao;
import com.oys.pojo.User;
import java.sql.*;

public class UserDaoImpl implements UserDao {

    private BaseDao baseDao = new BaseDao();

    @Override
    public User findUser(String username) {
        String sql = "select * from user where user=?";
        User user = null;
        baseDao.open();
        ResultSet rs = baseDao.execDQL(sql, username);
        try{
            if (rs.next()){
                user = new User(username, rs.getString("password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        baseDao.close();
        return user;
    }

    @Override
    public boolean addUser(String username, String psw) {
        String sql = "INSERT INTO user(user,password) VALUES(?,?)";
        baseDao.open();
        int count = baseDao.execDML(sql, username, psw);
        baseDao.close();
        return count == 1 ? true : false;
    }
}
