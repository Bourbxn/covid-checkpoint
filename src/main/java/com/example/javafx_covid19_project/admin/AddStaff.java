package com.example.javafx_covid19_project.admin;

import com.example.javafx_covid19_project.DatabaseConnection;
import com.example.javafx_covid19_project.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;

public class AddStaff {
    @FXML private TextField username_staff;
    @FXML private TextField password_staff;
    @FXML private Button create_staff_btn;
    @FXML private Button go_back_menu_btn;

    public void staffCreateAccount(ActionEvent event)throws IOException{
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        createAccount(connectDB);
    }

    public void goBackMenu(ActionEvent event) throws IOException{
        Main m = new Main();
        m.changeScene("MenuAdmin.fxml");
        System.out.println("go back menu");
    }

    private void createAccount(Connection connectDB) throws IOException {
        Main m = new Main();

        try{
            String connectQuery = "INSERT INTO user (username, password, role) VALUES (?,?,?)";
            PreparedStatement pst = connectDB.prepareStatement(connectQuery);
            pst.setString(1, username_staff.getText());
            pst.setString(2, password_staff.getText());
            pst.setString(3, "STAFF");
            pst.executeUpdate();
            System.out.println("Success!");
            m.changeScene("MenuAdmin.fxml");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
