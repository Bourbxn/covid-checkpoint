package com.example.javafx_covid19_project.member;

import com.example.javafx_covid19_project.DatabaseConnection;
import com.example.javafx_covid19_project.Main;
import com.example.javafx_covid19_project.Pages;
import com.example.javafx_covid19_project.staff.TimelineListTable;
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


public class CheckPoint extends Pages implements Initializable {
    @FXML private TextField find_location;
    @FXML private Button find_btn;
    @FXML private Button back_to_menu_btn;
    @FXML private TableView<CheckPointTable> timeline_tb;
    @FXML private TableColumn<CheckPointTable, String> col_date;
    @FXML private TableColumn<CheckPointTable, String> col_timestart;
    @FXML private TableColumn<CheckPointTable, String> col_timeend;
    @FXML private TableColumn<CheckPointTable, String> col_sickness;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        col_date.setCellValueFactory(new PropertyValueFactory<CheckPointTable,String>("date"));
        col_timestart.setCellValueFactory(new PropertyValueFactory<CheckPointTable,String>("timeStart"));
        col_timeend.setCellValueFactory(new PropertyValueFactory<CheckPointTable,String>("timeEnd"));
        col_sickness.setCellValueFactory(new PropertyValueFactory<CheckPointTable,String>("sickness"));
    }

    public void findLocation(ActionEvent event) throws IOException {
        ObservableList<CheckPointTable> timelineListTable = getTimelineCheckpointTable();
        col_date.setCellValueFactory(new PropertyValueFactory<CheckPointTable,String>("date"));
        col_timestart.setCellValueFactory(new PropertyValueFactory<CheckPointTable,String>("timeStart"));
        col_timeend.setCellValueFactory(new PropertyValueFactory<CheckPointTable,String>("timeEnd"));
        col_sickness.setCellValueFactory(new PropertyValueFactory<CheckPointTable,String>("sickness"));
        timeline_tb.setItems(timelineListTable);
    }

    private ObservableList<CheckPointTable> getTimelineCheckpointTable(){
        ObservableList<CheckPointTable> timelineCheckpoint = FXCollections.observableArrayList();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String connectQuery = String.format("SELECT username, datetime_start, datetime_end, sickness FROM timeline_covid WHERE location = '%s' ORDER BY datetime_start",find_location.getText());

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
                String sickness = changeSicknessToStr(queryOutput.getString("sickness"));
                timelineCheckpoint.add(new CheckPointTable(date, timeStart, timeEnd, sickness));
            }
            System.out.println("Success!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timelineCheckpoint;
    }

    private String changeSicknessToStr(String sickness){
        return switch (sickness) {
            case "0" -> "Yes";
            case "1" -> "No";
            default -> null;
        };
    }

    public void goBackToMenu(ActionEvent event) throws IOException{
        Main m = new Main();
        Menu menu = new Menu();
        m.changeScenePassValue("Menu.fxml",menu,getUserLoggedIn());
    }

}
