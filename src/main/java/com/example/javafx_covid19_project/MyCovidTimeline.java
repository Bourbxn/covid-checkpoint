package com.example.javafx_covid19_project;

import com.example.javafx_covid19_project.member.Menu;
import com.example.javafx_covid19_project.staff.TimelineListTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MyCovidTimeline extends Pages implements AutoInitialize {
    private String round_of_covid;
    @FXML private ComboBox<String> round_of_covid_cbb;
    @FXML private TableView<MyCovidTimelineTable> my_covid_tb;
    @FXML private TableColumn<MyCovidTimelineTable, String> col_location;
    @FXML private TableColumn<MyCovidTimelineTable, String> col_date;
    @FXML private TableColumn<MyCovidTimelineTable, String> col_time_start;
    @FXML private TableColumn<MyCovidTimelineTable, String> col_time_end;
    @FXML private TableColumn<MyCovidTimelineTable, String> col_sickness;
    @FXML private Button go_back_menu_btn;




    @Override
    public void autoInitialize() {
        getRoundOfCovid();
        setRoundOfCovid();
    }

    public void showMyCovidTimeline(){
        ObservableList<MyCovidTimelineTable> timelineListTable = getTimelineListTable();
        col_date.setCellValueFactory(new PropertyValueFactory<MyCovidTimelineTable,String>("date"));
        col_time_start.setCellValueFactory(new PropertyValueFactory<MyCovidTimelineTable,String>("timeStart"));
        col_time_end.setCellValueFactory(new PropertyValueFactory<MyCovidTimelineTable,String>("timeEnd"));
        col_location.setCellValueFactory(new PropertyValueFactory<MyCovidTimelineTable,String>("location"));
        col_sickness.setCellValueFactory(new PropertyValueFactory<MyCovidTimelineTable,String>("sickness"));
        my_covid_tb.setItems(timelineListTable);
    }

    private ObservableList<MyCovidTimelineTable> getTimelineListTable(){
        ObservableList<MyCovidTimelineTable> timelineList = FXCollections.observableArrayList();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String connectQuery = String.format("SELECT datetime_start,datetime_end,location,sickness FROM timeline_covid WHERE username = '%s' AND covid_round = %s ORDER BY datetime_start",getUserLoggedIn(),round_of_covid_cbb.getValue());
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
                String sickness = changeSicknessToStr(queryOutput.getString("sickness"));
                timelineList.add(new MyCovidTimelineTable(location,date,timeStart,timeEnd,sickness));
            }
            System.out.println("Success!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timelineList;
    }

    private void getRoundOfCovid(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String connectQuery = String.format("SELECT covid_round FROM user_member WHERE username = '%s'",getUserLoggedIn());
        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            while (queryOutput.next()){
                this.round_of_covid = queryOutput.getString("covid_round");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setRoundOfCovid(){
        ArrayList<String> roundOfCovidList = new ArrayList<>();
        for(int i=0;i<Integer.parseInt(round_of_covid);i++){
            roundOfCovidList.add((String.valueOf(i+1)));
        }
        round_of_covid_cbb.setItems(FXCollections.observableArrayList(roundOfCovidList));
    }

    private String changeSicknessToStr(String sickness){
        return switch (sickness) {
            case "0" -> "Yes";
            case "1" -> "No";
            default -> null;
        };
    }

    public void goBackMenuMyCovidTimeline(ActionEvent event) throws IOException{
        Main m = new Main();
        Menu menu = new Menu();
        m.changeScenePassValue("Menu.fxml",menu,getUserLoggedIn());
    }
}
