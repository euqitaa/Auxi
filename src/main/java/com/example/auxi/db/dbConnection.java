package com.example.auxi.db;
import java.sql.*;
public class dbConnection {
    public static Connection getCOnnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/auxidb","root","");
    }
}
