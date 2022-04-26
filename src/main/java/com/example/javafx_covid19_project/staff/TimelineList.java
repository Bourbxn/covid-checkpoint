package com.example.javafx_covid19_project.staff;

import com.example.javafx_covid19_project.DatabaseConnection;
import com.example.javafx_covid19_project.Main;
import com.example.javafx_covid19_project.Pages;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class TimelineList extends Pages implements Initializable {
    @FXML private ListView<String> timeline_list_lv;
    @FXML private Button go_back_menu_timeline;
    @FXML private TableView<TimelineListTable> timeline_tb;
    @FXML private TableColumn<TimelineListTable, String> col_location;
    @FXML private TableColumn<TimelineListTable, String> col_date;
    @FXML private TableColumn<TimelineListTable, String> col_timestart;
    @FXML private TableColumn<TimelineListTable, String> col_timeend;
    @FXML private TableColumn<TimelineListTable, String> col_name;
    @FXML private TableColumn<TimelineListTable, String> col_gender;
    @FXML private TableColumn<TimelineListTable, String> col_age;


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
        timeline_tb.setItems(timelineListTable);
    }

    private ObservableList<TimelineListTable> getTimelineListTable(){
        ObservableList<TimelineListTable> timelineList = FXCollections.observableArrayList();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String connectQuery = "SELECT username, datetime_start,datetime_end,location FROM timeline_covid ORDER BY datetime_start";
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
                timelineList.add(new TimelineListTable(location,date,timeStart,timeEnd,name,gender,age));
            }
            System.out.println("Success!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timelineList;
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
