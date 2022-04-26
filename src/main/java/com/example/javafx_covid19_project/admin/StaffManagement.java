package com.example.javafx_covid19_project.admin;

import com.example.javafx_covid19_project.DatabaseConnection;
import com.example.javafx_covid19_project.Main;
import com.example.javafx_covid19_project.Pages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class StaffManagement extends Pages implements Initializable {
    @FXML ListView<String> staff_lv;
    @FXML Button back_to_menu_btn;
    @FXML Button remove_staff_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        addStaffList();
    }

    public void addStaffList() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        staff_lv.getItems().clear();

        String connectQuery = "SELECT username FROM user WHERE role = 'STAFF' ORDER BY username";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            while (queryOutput.next()){
                String usernameStaff = queryOutput.getString("username");
                staff_lv.getItems().addAll(usernameStaff);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void backMenuStaffManage(ActionEvent event) throws IOException{
        Main m = new Main();
        m.changeScene("MenuAdmin.fxml");
    }

    public void removeStaff(ActionEvent event) throws IOException{
        String staffName = staff_lv.getSelectionModel().getSelectedItem();
        int selectedID = staff_lv.getSelectionModel().getSelectedIndex();
        staff_lv.getItems().remove(selectedID);
        removeStaffDB(staffName);

    }

    private void removeStaffDB(String username){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String connectQuery = String.format("DELETE FROM user WHERE username = '%s'",username);
        try{
            PreparedStatement pst = connectDB.prepareStatement(connectQuery);
            pst.executeUpdate();
            System.out.println("Success!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
