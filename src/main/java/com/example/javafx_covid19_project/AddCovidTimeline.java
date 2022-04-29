package com.example.javafx_covid19_project;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCovidTimeline extends Pages implements Initializable {
    @FXML private DatePicker date;
    @FXML private ComboBox<String> hour_start;
    @FXML private ComboBox<String> min_start;
    @FXML private ComboBox<String > hour_end;
    @FXML private ComboBox<String> min_end;
    @FXML private TextField location_tf;
    @FXML private CheckBox sickness;
    @FXML private Button add_btn;
    @FXML private Button remove_btn;
    @FXML private Button save_btn;

    private final String[] hourList = {"00","01","02","03","04","05","06","07","08","09","10","11","12",
            "13","14","15","16","17","18","19","20","21","22","23"};
    private final String[] minList = {"00","01","02","03","04","05","06","07","08","09","10","11","12",
            "13","14","15","16","17","18","19","20","21","22","23","24",
            "25","26","27","28","29","30","31","32","33","34","35","36",
            "37","38","39","40","41","42","43","44","45","46","47","48",
            "49","50","51","52","53","54","55","56","57","58","59"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hour_start.setItems(FXCollections.observableArrayList(hourList));
        min_start.setItems(FXCollections.observableArrayList(minList));
        hour_end.setItems(FXCollections.observableArrayList(hourList));
        min_end.setItems(FXCollections.observableArrayList(minList));
    }
}
