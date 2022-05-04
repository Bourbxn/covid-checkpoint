package com.example.javafx_covid19_project;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection(){
        String databaseName = "checkpoint";
        String databaseUsername = "root";
        String databasePassword = "VwmCRG2nuWhGp9aA";
        String url = "jdbc:mysql://161.246.5.40:3777/" + databaseName;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUsername, databasePassword);
        }catch (Exception e){
            e.printStackTrace();
        }

        return databaseLink;

    }
}
