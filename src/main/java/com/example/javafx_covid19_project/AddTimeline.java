package com.example.javafx_covid19_project;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddTimeline extends Pages implements Initializable {
    private final String[] hourList = {"00","01","02","03","04","05","06","07","08","09","10","11","12",
            "13","14","15","16","17","18","19","20","21","22","23"};
    private final String[] minList = {"00","01","02","03","04","05","06","07","08","09","10","11","12",
            "13","14","15","16","17","18","19","20","21","22","23","24",
            "25","26","27","28","29","30","31","32","33","34","35","36",
            "37","38","39","40","41","42","43","44","45","46","47","48",
            "49","50","51","52","53","54","55","56","57","58","59"};

    @FXML private Button add_timeline_btn;
    @FXML private Button back_to_menu;

    @FXML private DatePicker date_1;
    @FXML private ComboBox<String> hour_start_1;
    @FXML private ComboBox<String> min_start_1;
    @FXML private ComboBox<String > hour_end_1;
    @FXML private ComboBox<String> min_end_1;
    @FXML private TextField location_1;
    @FXML private CheckBox sickness_1;

    @FXML private DatePicker date_2;
    @FXML private ComboBox<String> hour_start_2;
    @FXML private ComboBox<String> min_start_2;
    @FXML private ComboBox<String > hour_end_2;
    @FXML private ComboBox<String> min_end_2;
    @FXML private TextField location_2;
    @FXML private CheckBox sickness_2;

    @FXML private DatePicker date_3;
    @FXML private ComboBox<String> hour_start_3;
    @FXML private ComboBox<String> min_start_3;
    @FXML private ComboBox<String > hour_end_3;
    @FXML private ComboBox<String> min_end_3;
    @FXML private TextField location_3;
    @FXML private CheckBox sickness_3;

    @FXML private DatePicker date_4;
    @FXML private ComboBox<String> hour_start_4;
    @FXML private ComboBox<String> min_start_4;
    @FXML private ComboBox<String > hour_end_4;
    @FXML private ComboBox<String> min_end_4;
    @FXML private TextField location_4;
    @FXML private CheckBox sickness_4;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        setHourMinComboBox(hour_start_1,min_start_1,hour_end_1,min_end_1);
        setHourMinComboBox(hour_start_2,min_start_2,hour_end_2,min_end_2);
        setHourMinComboBox(hour_start_3,min_start_3,hour_end_3,min_end_3);
        setHourMinComboBox(hour_start_4,min_start_4,hour_end_4,min_end_4);
    }


    @FXML
    private void addTimeline(ActionEvent event) throws IOException{
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String username = getUserLoggedIn();
        checkBeforeAddToDB(username,datetimeToStr(date_1,hour_start_1,min_start_1), datetimeToStr(date_1,hour_end_1,min_end_1),
                location_1.getText(),sickToStr(sickness_1.isSelected()),connectDB);
        checkBeforeAddToDB(username,datetimeToStr(date_2,hour_start_2,min_start_2), datetimeToStr(date_2,hour_end_2,min_end_2),
                location_2.getText(),sickToStr(sickness_2.isSelected()),connectDB);
        checkBeforeAddToDB(username,datetimeToStr(date_3,hour_start_3,min_start_3), datetimeToStr(date_3,hour_end_3,min_end_3),
                location_3.getText(),sickToStr(sickness_3.isSelected()),connectDB);
        checkBeforeAddToDB(username,datetimeToStr(date_4,hour_start_4,min_start_4), datetimeToStr(date_4,hour_end_4,min_end_4),
                location_4.getText(),sickToStr(sickness_4.isSelected()),connectDB);

    }

    private void checkBeforeAddToDB(String username, String datetime_start, String datetime_end,String location, String sickness, Connection connection){
        if(isEmpty(username)||isEmpty(datetime_start)||isEmpty(datetime_end)||isEmpty(location)){
            System.out.println("Error, have some null!");
        }
        else {
            System.out.println("go to Add timeline process");
            addTimelineToDB(username,datetime_start,datetime_end,location,sickness,connection);
        }
    }

    private void addTimelineToDB(String username, String datetime_start, String datetime_end,String location, String sickness, Connection connectDB){
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

    private void setHourMinComboBox(ComboBox<String> hourStart,ComboBox<String> minStart,ComboBox<String> hourEnd,ComboBox<String> minEnd){
        hourStart.setItems(FXCollections.observableArrayList(hourList));
        minStart.setItems(FXCollections.observableArrayList(minList));
        hourEnd.setItems(FXCollections.observableArrayList(hourList));
        minEnd.setItems(FXCollections.observableArrayList(minList));
    }

    public void backMenuFromAddTimeline(ActionEvent event) throws IOException{
        Main m = new Main();
        m.changeScene("Menu.fxml");
    }




}
