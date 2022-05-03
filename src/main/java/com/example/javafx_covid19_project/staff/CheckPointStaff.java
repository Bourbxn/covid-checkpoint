package com.example.javafx_covid19_project.staff;

import com.example.javafx_covid19_project.AutoInitialize;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class CheckPointStaff extends Pages implements Initializable, AutoInitialize {
    @FXML private TextField find_location;
    @FXML private Button find_btn;
    @FXML private Button back_to_menu_btn;
    @FXML private Button timeline_list_btn;
    @FXML private Line search_line;
    @FXML private TableView<CheckPointStaffTable> timeline_tb;
    @FXML private TableColumn<CheckPointStaffTable, String> col_date;
    @FXML private TableColumn<CheckPointStaffTable, String> col_timestart;
    @FXML private TableColumn<CheckPointStaffTable, String> col_timeend;
    @FXML private TableColumn<CheckPointStaffTable, String> col_name;
    @FXML private TableColumn<CheckPointStaffTable, String> col_gender;
    @FXML private TableColumn<CheckPointStaffTable, String> col_age;
    @FXML private TableColumn<CheckPointStaffTable, String> col_sickness;


    @Override
    public void autoInitialize() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        col_date.setCellValueFactory(new PropertyValueFactory<CheckPointStaffTable,String>("date"));
        col_timestart.setCellValueFactory(new PropertyValueFactory<CheckPointStaffTable,String>("timeStart"));
        col_timeend.setCellValueFactory(new PropertyValueFactory<CheckPointStaffTable,String>("timeEnd"));
        col_name.setCellValueFactory(new PropertyValueFactory<CheckPointStaffTable,String>("name"));
        col_gender.setCellValueFactory(new PropertyValueFactory<CheckPointStaffTable,String>("gender"));
        col_age.setCellValueFactory(new PropertyValueFactory<CheckPointStaffTable,String>("age"));
        col_sickness.setCellValueFactory(new PropertyValueFactory<CheckPointStaffTable,String>("sickness"));

    }

    public void findLocationStaff(ActionEvent event) throws IOException {
        ObservableList<CheckPointStaffTable> timelineListTable = getTimelineCheckpointTable();
        col_date.setCellValueFactory(new PropertyValueFactory<CheckPointStaffTable,String>("date"));
        col_timestart.setCellValueFactory(new PropertyValueFactory<CheckPointStaffTable,String>("timeStart"));
        col_timeend.setCellValueFactory(new PropertyValueFactory<CheckPointStaffTable,String>("timeEnd"));
        col_name.setCellValueFactory(new PropertyValueFactory<CheckPointStaffTable,String>("name"));
        col_gender.setCellValueFactory(new PropertyValueFactory<CheckPointStaffTable,String>("gender"));
        col_age.setCellValueFactory(new PropertyValueFactory<CheckPointStaffTable,String>("age"));
        col_sickness.setCellValueFactory(new PropertyValueFactory<CheckPointStaffTable,String>("sickness"));
        timeline_tb.setItems(timelineListTable);
    }

    private ObservableList<CheckPointStaffTable> getTimelineCheckpointTable(){
        ObservableList<CheckPointStaffTable> timelineCheckpoint = FXCollections.observableArrayList();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String connectQuery = String.format("SELECT username, datetime_start,datetime_end,location, sickness FROM timeline_covid WHERE location = '%s' ORDER BY datetime_start DESC",find_location.getText());

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
                String sickness = changeSicknessToStr(queryOutput.getString("sickness"));
                timelineCheckpoint.add(new CheckPointStaffTable(date,timeStart,timeEnd,name,gender,age,sickness));
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

    private String changeSicknessToStr(String sickness){
        return switch (sickness) {
            case "0" -> "Yes";
            case "1" -> "No";
            default -> null;
        };
    }

    public void changeLocationColorStaff(){
        search_line.setStroke(Color.rgb(0,137,150));
    }
}
