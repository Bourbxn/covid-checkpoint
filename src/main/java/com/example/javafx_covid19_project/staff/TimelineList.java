package com.example.javafx_covid19_project.staff;

import com.example.javafx_covid19_project.DatabaseConnection;
import com.example.javafx_covid19_project.Pages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class TimelineList extends Pages implements Initializable {
    @FXML private ListView<String> timeline_list_lv;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        addTimelineList();
    }

    public void addTimelineList() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        timeline_list_lv.getItems().clear();
        timeline_list_lv.getItems().add("\t\tlocation\t\t\t\tdate\t\t\t\t\t\ttime start\t\t\t\ttime end");

        String connectQuery = "SELECT datetime_start,datetime_end,location FROM timeline_covid";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            while (queryOutput.next()){
                String location = queryOutput.getString("location");
                String dateTimeStart = queryOutput.getString("datetime_start");
                String dateTimeEnd = queryOutput.getString("datetime_end");
                String date = (dateTimeStart.split(" ")[0]).split("-")[2] + "/"
                        + (dateTimeStart.split(" ")[0]).split("-")[1] + "/"
                        + (dateTimeStart.split(" ")[0]).split("-")[0] ;
                String timeStart = (dateTimeStart.split(" ")[1]).split(":")[0]
                        +":"+(dateTimeStart.split(" ")[1]).split(":")[1];
                String timeEnd = (dateTimeEnd.split(" ")[1]).split(":")[0]
                        +":"+(dateTimeEnd.split(" ")[1]).split(":")[1];
                String listOut = "\t\t" + location + "\t\t\t\t\t"+ date + "\t\t\t\t" + timeStart + "\t\t\t\t\t" + timeEnd;
                timeline_list_lv.getItems().addAll(listOut);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("find location!");
    }

}
