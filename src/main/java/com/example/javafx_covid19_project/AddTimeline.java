package com.example.javafx_covid19_project;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        hour_start_1.setItems(FXCollections.observableArrayList(hourList));
        min_start_1.setItems(FXCollections.observableArrayList(minList));
        hour_end_1.setItems(FXCollections.observableArrayList(hourList));
        min_end_1.setItems(FXCollections.observableArrayList(minList));
    }


    @FXML
    private void addTimeline(ActionEvent event) throws IOException{
        String username = getUserLoggedIn();

        checkBeforeAddToDB(username,datetimeToStr(date_1,hour_start_1,min_start_1), datetimeToStr(date_1,hour_end_1,min_end_1),
                location_1.getText(),sickToStr(sickness_1.isSelected()));

    }

    private void checkBeforeAddToDB(String username, String datetime_start, String datetime_end,String location, String sickness){
        if(isEmpty(username)||isEmpty(datetime_start)||isEmpty(datetime_end)||isEmpty(location)){
            System.out.println("Error, have some null!");
        }
        else {
            System.out.println("go to Add timeline process");
            addTimelineToDB(username,datetime_start,datetime_end,location,sickness);
        }
    }

    private void addTimelineToDB(String username, String datetime_start, String datetime_end,String location, String sickness){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        try{
            String connectQuery = "INSERT INTO timeline (username, datetime_start, datetime_end, location, sickness) VALUES (?,?,?,?,?)";
            PreparedStatement pst = connectDB.prepareStatement(connectQuery);
            pst.setString(1, username);
            pst.setString(2, datetime_start);
            pst.setString(3, datetime_end);
            pst.setString(4, location);
            pst.setString(5, sickness);
            pst.executeUpdate();
            System.out.println("Add timeline Success!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private String sickToStr(boolean sickness){
        if(sickness){
            return "1";
        }
        else{
            return "0";
        }
    }

    private String datetimeToStr(DatePicker datePicker,ComboBox<String> comboBox_1,ComboBox<String> comboBox_2){
        if(isDatePickerNull(datePicker)||isComboBoxNull(comboBox_1)||isComboBoxNull(comboBox_2)){
            return "";
        }
        else {
            return String.format("%s %s:%s:00",datePicker.getValue().toString(),comboBox_1.getValue(),comboBox_2.getValue());
        }
    }

    private boolean isEmpty(String text){
        return text.compareTo("") == 0;
    }

    private boolean isComboBoxNull(ComboBox<String> comboBox){
        return comboBox.getValue() == null;
    }

    private boolean isDatePickerNull(DatePicker datePicker){
        return datePicker.getValue() == null;
    }


}
