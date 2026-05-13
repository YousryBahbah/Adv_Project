package com.radi.demo7;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/ecommerce?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "yousry101";

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}