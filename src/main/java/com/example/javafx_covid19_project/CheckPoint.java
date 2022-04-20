package com.example.javafx_covid19_project;

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


public class CheckPoint extends Pages implements Initializable {
    @FXML private TextField find_location;
    @FXML private Button find_btn;
    @FXML private ListView<String> time_list_view;
    @FXML private Button back_to_menu_btn;

    //ArrayList<String> timeFromLocation = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }

    public void findLocation(ActionEvent event) throws IOException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        time_list_view.getItems().clear();
        time_list_view.getItems().add("\tdate\t\t\t\ttime start\t\ttime end");

        String connectQuery = String.format("SELECT datetime_start,datetime_end FROM timeline WHERE location ='%s' ORDER BY datetime_start",find_location.getText());

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
                String listOut = "\t"+ date + "\t\t" + timeStart + "\t\t\t" + timeEnd;
                time_list_view.getItems().addAll(listOut);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("find location!");
    }

    public void goBackToMenu(ActionEvent event) throws IOException{
        Main m = new Main();
        m.changeScene("Menu.fxml");
    }
}
