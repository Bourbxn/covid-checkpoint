package com.example.javafx_covid19_project.member;

import com.example.javafx_covid19_project.AutoInitialize;
import com.example.javafx_covid19_project.DatabaseConnection;
import com.example.javafx_covid19_project.Main;
import com.example.javafx_covid19_project.Pages;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.sql.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddTimeline extends Pages implements Initializable, AutoInitialize {
    private final String[] hourList = {"00","01","02","03","04","05","06","07","08","09","10","11","12",
            "13","14","15","16","17","18","19","20","21","22","23"};
    private final String[] minList = {"00","01","02","03","04","05","06","07","08","09","10","11","12",
            "13","14","15","16","17","18","19","20","21","22","23","24",
            "25","26","27","28","29","30","31","32","33","34","35","36",
            "37","38","39","40","41","42","43","44","45","46","47","48",
            "49","50","51","52","53","54","55","56","57","58","59"};


    @Override
    public void autoInitialize() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
