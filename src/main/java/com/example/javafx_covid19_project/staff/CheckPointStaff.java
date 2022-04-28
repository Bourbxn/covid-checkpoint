package com.example.javafx_covid19_project.staff;

import com.example.javafx_covid19_project.DatabaseConnection;
import com.example.javafx_covid19_project.Main;
import com.example.javafx_covid19_project.Pages;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class CheckPointStaff extends Pages implements Initializable {
    @FXML private TextField find_location;
    @FXML private Button find_btn;
    @FXML private Button back_to_menu_btn;
    @FXML private TableView<TimelineListTable> timeline_tb;
    @FXML private TableColumn<TimelineListTable, String> col_date;
    @FXML private TableColumn<TimelineListTable, String> col_timestart;
    @FXML private TableColumn<TimelineListTable, String> col_timeend;
    @FXML private TableColumn<TimelineListTable, String> col_name;
    @FXML private TableColumn<TimelineListTable, String> col_gender;
    @FXML private TableColumn<TimelineListTable, String> col_age;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        col_date.setCellValueFactory(new PropertyValueFactory<TimelineListTable,String>("date"));
        col_timestart.setCellValueFactory(new PropertyValueFactory<TimelineListTable,String>("timeStart"));
        col_timeend.setCellValueFactory(new PropertyValueFactory<TimelineListTable,String>("timeEnd"));
        col_name.setCellValueFactory(new PropertyValueFactory<TimelineListTable,String>("name"));
        col_gender.setCellValueFactory(new PropertyValueFactory<TimelineListTable,String>("gender"));
        col_age.setCellValueFactory(new PropertyValueFactory<TimelineListTable,String>("age"));
    }

    public void findLocationStaff(ActionEvent event) throws IOException {
        ObservableList<TimelineListTable> timelineListTable = getTimelineCheckpointTable();
        col_date.setCellValueFactory(new PropertyValueFactory<TimelineListTable,String>("date"));
        col_timestart.setCellValueFactory(new PropertyValueFactory<TimelineListTable,String>("timeStart"));
        col_timeend.setCellValueFactory(new PropertyValueFactory<TimelineListTable,String>("timeEnd"));
        col_name.setCellValueFactory(new PropertyValueFactory<TimelineListTable,String>("name"));
        col_gender.setCellValueFactory(new PropertyValueFactory<TimelineListTable,String>("gender"));
        col_age.setCellValueFactory(new PropertyValueFactory<TimelineListTable,String>("age"));
        timeline_tb.setItems(timelineListTable);
    }

    private ObservableList<TimelineListTable> getTimelineCheckpointTable(){
        ObservableList<TimelineListTable> timelineCheckpoint = FXCollections.observableArrayList();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String connectQuery = String.format("SELECT username, datetime_start,datetime_end,location FROM timeline_covid WHERE location = '%s' ORDER BY datetime_start",find_location.getText());

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            while (queryOutput.next()){
                String dateTimeStart = queryOutput.getString("datetime_start");
                String dateTimeEnd = queryOutput.getString("datetime_end");
                String date = (dateTimeStart.split(" ")[0]).split("-")[2] + "/"
                        + (dateTimeStart.split(" ")[0]).split("-")[1] + "/"
                        + (dateTimeStart.split(" ")[0]).split("-")[0] ;
                String timeStart = (dateTimeStart.split(" ")[1]).split(":")[0]
                        +":"+(dateTimeStart.split(" ")[1]).split(":")[1];
                String timeEnd = (dateTimeEnd.split(" ")[1]).split(":")[0]
                        +":"+(dateTimeEnd.split(" ")[1]).split(":")[1];
                String name = getNameDB(queryOutput.getString("username"))[0] + "  " + getNameDB(queryOutput.getString("username"))[1];
                String gender = getNameDB(queryOutput.getString("username"))[3];
                String age = getNameDB(queryOutput.getString("username"))[2];
                timelineCheckpoint.add(new TimelineListTable(date,timeStart,timeEnd,name,gender,age));
            }
            System.out.println("Success!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timelineCheckpoint;
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

    public void goBackToMenu(ActionEvent event) throws IOException{
        Main m = new Main();
        m.changeScene("MenuStaff.fxml");
    }
}
