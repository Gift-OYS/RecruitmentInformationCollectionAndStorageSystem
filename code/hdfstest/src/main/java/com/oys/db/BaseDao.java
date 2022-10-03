package com.oys.db;

import com.oys.pojo.User;

import java.sql.*;

public class BaseDao {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/hdfs?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

    private Connection conn;
    private PreparedStatement pm;
    private ResultSet rs;

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void open() {
        try {
            this.conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if(rs!=null) {
            try {
                this.rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                this.rs = null;
            }
        }

        if(pm!=null) {
            try {
                this.pm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                this.pm = null;
            }
        }

        if(conn!=null) {
            try {
                this.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                this.conn = null;
            }
        }
    }

    public void makeStatement(String sql,Object...param) {
        try {
            this.pm = this.conn.prepareStatement(sql);
            for(int i=0;i<param.length;i++)
                this.pm.setObject(i+1, param[i]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int execDML(String sql,Object...param) {
        try {
            this.makeStatement(sql, param);
            return this.pm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public ResultSet execDQL(String sql,Object...param) {
        try {
            this.makeStatement(sql, param);
            return this.pm.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
