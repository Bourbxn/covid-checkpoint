package com.example.javafx_covid19_project.staff;

import com.example.javafx_covid19_project.DatabaseConnection;
import com.example.javafx_covid19_project.Main;
import com.example.javafx_covid19_project.Pages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    @FXML private Button go_back_menu_timeline;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        addTimelineList();
    }

    public void addTimelineList() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        timeline_list_lv.getItems().clear();
        timeline_list_lv.getItems().add("\t\tlocation\t\t\t\tdate\t\t\t\t\t\ttime start\t\t\t\ttime end\t\t\tname\t\t\t\t\t\tgender\t\t\tage");

        String connectQuery = "SELECT username, datetime_start,datetime_end,location FROM timeline_covid ORDER BY datetime_start ";

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
                String name = getNameDB(queryOutput.getString("username"))[0] + " " + getNameDB(queryOutput.getString("username"))[1];
                String gender = getNameDB(queryOutput.getString("username"))[3];
                String age = getNameDB(queryOutput.getString("username"))[2];
                String listOut = "\t\t" + location + "\t\t\t\t\t"+ date + "\t\t\t\t" + timeStart + "\t\t\t\t\t" + timeEnd + "\t\t\t\t" + name + "\t\t\t\t\t" + gender
                        + "\t\t\t" + age;
                timeline_list_lv.getItems().addAll(listOut);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("find location!");
    }

    private String[] getNameDB(String username){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String connectQuery = String.format("SELECT first_name, last_name, age, gender FROM user_member WHERE username = '%s'",username);
        String[] dataDB = new String[4];
        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            while (queryOutput.next()){
                String[] columnLabel = {"first_name", "last_name", "age", "gender"};
                for(int i=0;i<4;i++) dataDB[i] = queryOutput.getString(columnLabel[i]);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataDB;
    }

    public void goBackMenuTimelineList(ActionEvent event) throws IOException{
        System.out.println("go back menu from timeline");
        Main m = new Main();
        m.changeScene("MenuStaff.fxml");
    }
}
