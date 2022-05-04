package com.example.javafx_covid19_project.member;

import com.example.javafx_covid19_project.AutoInitialize;
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
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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


public class CheckPoint extends Pages implements Initializable, AutoInitialize {
    @FXML private TextField find_location;
    @FXML private Button find_btn;
    @FXML private TableView<CheckPointTable> timeline_tb;
    @FXML private TableColumn<CheckPointTable, String> col_date;
    @FXML private TableColumn<CheckPointTable, String> col_timestart;
    @FXML private TableColumn<CheckPointTable, String> col_timeend;
    @FXML private TableColumn<CheckPointTable, String> col_sickness;
    @FXML private ComboBox<String> auto_complete_cbb;
    @FXML private Line search_line;
    @FXML private AnchorPane bg_app;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        auto_complete_cbb.setItems(FXCollections.observableArrayList("ISAG"));
        col_date.setCellValueFactory(new PropertyValueFactory<CheckPointTable,String>("date"));
        col_timestart.setCellValueFactory(new PropertyValueFactory<CheckPointTable,String>("timeStart"));
        col_timeend.setCellValueFactory(new PropertyValueFactory<CheckPointTable,String>("timeEnd"));
        col_sickness.setCellValueFactory(new PropertyValueFactory<CheckPointTable,String>("sickness"));
    }

    public void findLocation(ActionEvent event) throws IOException {
        System.out.println("enter tf");
        if(ISAG()){
            ObservableList<CheckPointTable> timelineListTable = getTimelineCheckpointTable();
            col_date.setCellValueFactory(new PropertyValueFactory<CheckPointTable,String>("date"));
            col_timestart.setCellValueFactory(new PropertyValueFactory<CheckPointTable,String>("timeStart"));
            col_timeend.setCellValueFactory(new PropertyValueFactory<CheckPointTable,String>("timeEnd"));
            col_sickness.setCellValueFactory(new PropertyValueFactory<CheckPointTable,String>("sickness"));
            timeline_tb.setItems(timelineListTable);
            clearInput();
        }
    }

    private void clearInput(){
        bg_app.requestFocus();
        search_line.setStroke(Color.BLACK);
    }

    private ObservableList<CheckPointTable> getTimelineCheckpointTable(){
        ObservableList<CheckPointTable> timelineCheckpoint = FXCollections.observableArrayList();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String connectQuery = String.format("SELECT username, datetime_start, datetime_end, sickness FROM timeline_covid WHERE location = '%s' ORDER BY datetime_start DESC",find_location.getText());

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


    @Override
    public void autoInitialize() {

    }

    public void changeLocationColor(){
        search_line.setStroke(Color.rgb(0,137,150));
    }

    private boolean ISAG(){
        return !checkSQLInjection(find_location.getText());
    }

    @FXML
    private void showAutoCompleteText(){
        find_location.requestFocus();
        auto_complete_cbb.getItems().clear();
        ArrayList<String> locationData = getDataAutoCompleteDB();
        auto_complete_cbb.setItems(FXCollections.observableArrayList(locationData));
        auto_complete_cbb.show();
    }

    private ArrayList<String> getDataAutoCompleteDB() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String connectQuery = String.format("SELECT MIN(id) AS id, location FROM timeline_covid WHERE location LIKE '%s%c' GROUP BY location LIMIT 10",find_location.getText(),'%');
        ArrayList<String> arrayListData = new ArrayList<>();
        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            while (queryOutput.next()){
               arrayListData.add(queryOutput.getString("location"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayListData;
    }

    @FXML private void setLocationSelected(ActionEvent actionEvent){
        System.out.println("enter cbb");
        find_location.setText(auto_complete_cbb.getSelectionModel().getSelectedItem());
        try {
            find_location.positionCaret(find_location.getText().length());
        }catch (Exception e){
            System.out.println("Size Error");
        }
    }
}
