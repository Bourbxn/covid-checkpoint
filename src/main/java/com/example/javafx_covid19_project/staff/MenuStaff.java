package com.example.javafx_covid19_project.staff;

import com.example.javafx_covid19_project.AutoInitialize;
import com.example.javafx_covid19_project.DatabaseConnection;
import com.example.javafx_covid19_project.Main;
import com.example.javafx_covid19_project.Pages;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class MenuStaff extends Pages implements AutoInitialize, Initializable {
    @FXML private Button timeline_list_btn;
    @FXML private Button check_point_btn;
    @FXML private Button logout_btn;
    @FXML private TableView<TimelineListTable> timeline_tb;
    @FXML private TableColumn<TimelineListTable, String> col_location;
    @FXML private TableColumn<TimelineListTable, String> col_date;
    @FXML private TableColumn<TimelineListTable, String> col_timestart;
    @FXML private TableColumn<TimelineListTable, String> col_timeend;
    @FXML private TableColumn<TimelineListTable, String> col_name;
    @FXML private TableColumn<TimelineListTable, String> col_gender;
    @FXML private TableColumn<TimelineListTable, String> col_age;
    @FXML private TableColumn<TimelineListTable, String> col_sickness;

    @Override
    public void autoInitialize() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        addTimelineTable();
    }

    private void addTimelineTable(){
        ObservableList<TimelineListTable> timelineListTable = getTimelineListTable();
        col_location.setCellValueFactory(new PropertyValueFactory<TimelineListTable,String>("location"));
        col_date.setCellValueFactory(new PropertyValueFactory<TimelineListTable,String>("date"));
        col_timestart.setCellValueFactory(new PropertyValueFactory<TimelineListTable,String>("timeStart"));
        col_timeend.setCellValueFactory(new PropertyValueFactory<TimelineListTable,String>("timeEnd"));
        col_name.setCellValueFactory(new PropertyValueFactory<TimelineListTable,String>("name"));
        col_gender.setCellValueFactory(new PropertyValueFactory<TimelineListTable,String>("gender"));
        col_age.setCellValueFactory(new PropertyValueFactory<TimelineListTable,String>("age"));
        col_sickness.setCellValueFactory(new PropertyValueFactory<TimelineListTable,String>("sickness"));
        timeline_tb.setItems(timelineListTable);
    }

    private ObservableList<TimelineListTable> getTimelineListTable(){
        ObservableList<TimelineListTable> timelineList = FXCollections.observableArrayList();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String connectQuery = "SELECT username, first_name, last_name, datetime_start,datetime_end,location, sickness, gender, age FROM timeline_covid ORDER BY datetime_start DESC";
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
                String name = queryOutput.getString("first_name") + "  " + queryOutput.getString("last_name");
                String gender = queryOutput.getString("gender");
                String age = queryOutput.getString("age");
                String sickness = changeSicknessToStr(queryOutput.getString("sickness"));
                timelineList.add(new TimelineListTable(location,date,timeStart,timeEnd,name,gender,age,sickness));
            }
            System.out.println("Success!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timelineList;
    }


    private String changeSicknessToStr(String sickness){
        return switch (sickness) {
            case "0" -> "Yes";
            case "1" -> "No";
            default -> null;
        };
    }


}
