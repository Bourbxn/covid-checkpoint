package com.example.javafx_covid19_project;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.*;

import java.io.IOException;
import java.util.ResourceBundle;

public class Register extends Pages implements Initializable {

    public Register() {

    }

    @FXML
    private Button button;
    @FXML
    private TextField first_name_register;
    @FXML
    private TextField last_name_register;
    @FXML
    private TextField age_register;
    @FXML
    private ComboBox<String> gender_register;
    @FXML
    private TextField username_register;
    @FXML
    private PasswordField password_register;
    @FXML
    private Hyperlink user_go_back_login;
    @FXML
    private Label gender_label;

    public void userCreateAccount(ActionEvent event) throws IOException {
        createAccount();
    }

    private void createAccount() throws IOException {
        Main m = new Main();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();


        try{
            String connectQuery = "INSERT INTO population (username, password, first_name,last_name, age, vaccine, gender) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pst = connectDB.prepareStatement(connectQuery);
            pst.setString(1, username_register.getText());
            pst.setString(2, password_register.getText());
            pst.setString(3, first_name_register.getText());
            pst.setString(4, last_name_register.getText());
            pst.setString(5, age_register.getText());
            pst.setString(6, "0");
            pst.setString(7, gender_register.getValue().toString());
            pst.executeUpdate();
            System.out.println("Success!");
            m.changeScene("Login.fxml");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void userGoBackLogin(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("Login.fxml");

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gender_register.setItems(FXCollections.observableArrayList("Male","Female","Other"));
    }

    public void hideGenderLabel(ActionEvent event){
        gender_label.setText("");
    }
}
