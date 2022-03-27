package com.example.javafx_covid19_project;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection(){
        String databaseName = "covid";
        String databaseUsername = "root";
        String databasePassword = "qwertyuiop[]";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUsername, databasePassword);
        }catch (Exception e){
            e.printStackTrace();
        }

        return databaseLink;

    }
}
