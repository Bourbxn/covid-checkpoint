package com.example.javafx_covid19_project.staff;

import com.example.javafx_covid19_project.DatabaseConnection;
import com.example.javafx_covid19_project.Main;
import com.example.javafx_covid19_project.Pages;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class CheckPointStaff extends Pages implements Initializable {
    @FXML private TextField find_location;
    @FXML private Button find_btn;
    @FXML private ListView<String> time_list_view;
    @FXML private Button back_to_menu_btn;

    //ArrayList<String> timeFromLocation = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }

    public void findLocationStaff(ActionEvent event) throws IOException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        time_list_view.getItems().clear();
        time_list_view.getItems().add("\t\tlocation\t\t\t\tdate\t\t\t\t\t\ttime start\t\t\t\ttime end\t\t\tname\t\t\t\t\t\tgender\t\t\tage");

        String connectQuery = String.format("SELECT username, datetime_start,datetime_end,location FROM timeline_covid WHERE location = '%s' ORDER BY datetime_start",find_location.getText());

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
                String listOut = "\t\t" + location + "\t\t\t\t\t"+ date + "\t\t\t\t" + timeStart + "\t\t\t\t\t" + timeEnd + "\t\t\t\t" + name + "\t\t\t\t\t" + gender
                        + "\t\t\t" + age;
                time_list_view.getItems().addAll(listOut);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("find location!");
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

    public void goBackToMenu(ActionEvent event) throws IOException{
        Main m = new Main();
        m.changeScene("MenuStaff.fxml");
    }
}
