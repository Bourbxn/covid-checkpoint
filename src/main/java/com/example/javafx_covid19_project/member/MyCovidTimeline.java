package com.example.javafx_covid19_project.member;

import com.example.javafx_covid19_project.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class MyCovidTimeline extends Pages implements AutoInitialize {
    private String round_of_covid;
    @FXML private ComboBox<String> round_of_covid_cbb;
    @FXML private TableView<MyCovidTimelineTable> my_covid_tb;
    @FXML private TableColumn<MyCovidTimelineTable, String> col_location;
    @FXML private TableColumn<MyCovidTimelineTable, String> col_date;
    @FXML private TableColumn<MyCovidTimelineTable, String> col_time_start;
    @FXML private TableColumn<MyCovidTimelineTable, String> col_time_end;
    @FXML private TableColumn<MyCovidTimelineTable, String> col_sickness;
    @FXML private Button check_point_btn;
    @FXML private Button add_timeline_btn;
    @FXML private Button logout_btn;
    @FXML private Button my_covid_timeline_btn;
    @FXML private Button profile_btn;
    @FXML private Button remove_timeline_btn;
    private String covid_round;

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
                timelineList.add(new MyCovidTimelineTable(date,timeStart,timeEnd,location,sickness));
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

    @FXML
    private void removeTimeline() throws IOException {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        removeTimelineDB(connectDB);
        resetCovidRoundDB(connectDB);
        Main m = new Main();
        Pages myCovidTimeline = new MyCovidTimeline();
        m.changeScenePassValue("MyCovidTimeline.fxml",myCovidTimeline,getUserLoggedIn());
        System.out.println("Go to my covid timeline with " + getUserLoggedIn());
    }

    private void removeTimelineDB(Connection connectDB){
        String connectQuery = String.format("DELETE FROM timeline_covid WHERE covid_round = '%s'",round_of_covid_cbb.getValue());
        try{
            PreparedStatement pst = connectDB.prepareStatement(connectQuery);
            pst.executeUpdate();
            System.out.println("Success!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void resetCovidRoundDB(Connection connectDB){
        setCovidRound(connectDB);
        addNewCovidRound(connectDB);
    }

    private void setCovidRound(Connection connectDB){
        String connectQuery = String.format("SELECT covid_round FROM user_member WHERE username = '%s'",getUserLoggedIn());
        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            while (queryOutput.next()){
                this.covid_round = queryOutput.getString("covid_round");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addNewCovidRound(Connection connectDB){
        covid_round = String.valueOf(Integer.parseInt(covid_round)-1);
        try{
            String connectQuery = String.format("UPDATE user_member SET covid_round = ? WHERE username = '%s'",getUserLoggedIn());
            PreparedStatement pst = connectDB.prepareStatement(connectQuery);
            pst.setString(1, covid_round);
            pst.executeUpdate();
            System.out.println("Add new covid round success!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
