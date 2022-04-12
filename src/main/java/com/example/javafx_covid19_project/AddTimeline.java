package com.example.javafx_covid19_project;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.*;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class AddTimeline extends Pages implements Initializable {
    private String[] hourList = {"1","2","3","4","5","6","7","8","9","10","11","12",
            "13","14","15","16","17","18","19","20","21","22","23","24"};
    private String[] minList = {"1","2","3","4","5","6","7","8","9","10","11","12",
            "13","14","15","16","17","18","19","20","21","22","23","24",
            "25","26","27","28","29","30","31","32","33","34","35","36",
            "37","38","39","40","41","42","43","44","45","46","47","48",
            "49","50","51","52","53","54","55","56","57","58","59","60"};

    @FXML
    private Text add_timeline_label;
    @FXML
    private DatePicker date_1;
    @FXML
    private ComboBox<String> hour_start_1;
    @FXML
    private Text split_start_1;
    @FXML
    private ComboBox<String> min_start_1;
    @FXML
    private Text to_1;
    @FXML
    private ComboBox<String > hour_end_1;
    @FXML
    private Text split_end_1;
    @FXML
    private ComboBox<String> min_end_1;
    @FXML
    private TextField location_1;
    @FXML
    private CheckBox sickness_1;
    @FXML
    private Button add_timeline_btn;
    private String username;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        hour_start_1.setItems(FXCollections.observableArrayList(hourList));
        min_start_1.setItems(FXCollections.observableArrayList(minList));
        hour_end_1.setItems(FXCollections.observableArrayList(hourList));
        min_end_1.setItems(FXCollections.observableArrayList(minList));
    }


    @FXML
    private void addTimeline(ActionEvent event) throws IOException{
        username = getUserLoggedIn();
        addTimelineToDB(username,"22/03/2565",1200,1300,"ECC",0);
    }

    private void addTimelineToDB(String username, String date, int time_start, int time_end,String location, int sickness){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        Date dateUtil = new Date();
        java.sql.Date sqlDate = new java.sql.Date(dateUtil.getTime());

        try{
            String connectQuery = "INSERT INTO timeline (username, date, time_start, time_end, location, sickness) VALUES (?,?,?,?,?,?)";
            PreparedStatement pst = connectDB.prepareStatement(connectQuery);
            pst.setString(1, username);
            pst.setString(2, "2022-10-8");
            pst.setString(3, "2000");
            pst.setString(4, "2200");
            pst.setString(5, "ECC");
            pst.setString(6, "0");
            pst.executeUpdate();
            System.out.println("Add timeline Success!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getUserAddTimeline(String username){
        this.username = username;
    }
}
