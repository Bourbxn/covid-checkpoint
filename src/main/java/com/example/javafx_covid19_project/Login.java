package com.example.javafx_covid19_project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.sql.Connection;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends Pages{

    public Login() {

    }

    @FXML
    private Button button;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Hyperlink createAccount;



    public void userLogin(ActionEvent event) throws IOException {
        checkLogin();

    }

    private void checkLogin() throws IOException {
        Main m = new Main();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String connectQuery = String.format("SELECT * FROM population WHERE username = '%s' AND password = '%s'"
        ,username.getText().toString(),password.getText().toString());

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            if(!queryOutput.isBeforeFirst()){
                System.out.println("Failed to login!");
            }
            else{
                Menu menu = new Menu();
                m.changeScenePassValue("Menu.fxml",menu,username.getText().toString());
                System.out.println("Successful to Login!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void userCreateAccount(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("Register.fxml");
    }

    /*
    private void changeSceneToMenu() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Parent root = loader.load();
        Menu menu = loader.getController();
        menu.getUserLoggedIn(username.getText().toString());
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }*/


}
