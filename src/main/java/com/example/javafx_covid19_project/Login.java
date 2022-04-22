package com.example.javafx_covid19_project;

import com.example.javafx_covid19_project.member.Menu;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

    private String role;



    public void userLogin(ActionEvent event) throws IOException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        setRoleDB(connectDB);
        checkLogin(connectDB);

    }

    private void checkLogin(Connection connectDB) throws IOException {
        Main m = new Main();

        String connectQuery = String.format("SELECT * FROM user WHERE username = '%s' AND password = '%s'"
                ,username.getText(),password.getText());

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            if(!queryOutput.isBeforeFirst()){
                System.out.println("Failed to login!");
            }
            else{
                com.example.javafx_covid19_project.member.Menu menu = new Menu();
                String page = getUserPage(role,"MenuAdmin.fxml","MenuStaff.fxml","Menu.fxml");
                m.changeScenePassValue(page,menu,username.getText());
                System.out.println("Successful to Login!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setRoleDB(Connection connectDB) throws IOException{
        String connectQuery = String.format("SELECT role FROM user WHERE username = '%s'",username.getText());
        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            while (queryOutput.next()){
                this.role = queryOutput.getString("role");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void userCreateAccount(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("Register.fxml");
    }

}
