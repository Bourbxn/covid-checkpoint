package com.example.javafx_covid19_project;

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

public class MemberManagement extends Pages implements Initializable {
    @FXML ListView<String> member_lv;
    @FXML Button back_to_menu_btn;
    @FXML Button remove_member_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        addTimelineList();
    }

    public void addTimelineList() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        member_lv.getItems().clear();

        String connectQuery = "SELECT username FROM user_member ORDER BY username";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            while (queryOutput.next()){
                String username = queryOutput.getString("username");
                member_lv.getItems().addAll(username);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void backMenuMemberManage(ActionEvent event) throws IOException{
        Main m = new Main();
        m.changeScene("MenuAdmin.fxml");
    }

    public void removeMember(ActionEvent event) throws IOException{
        String memberName = member_lv.getSelectionModel().getSelectedItem();
        int selectedID = member_lv.getSelectionModel().getSelectedIndex();
        member_lv.getItems().remove(selectedID);
        removeMemberDB(memberName);
        removeMemberDataDB(memberName);

    }

    private void removeMemberDB(String username){
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

    private void removeMemberDataDB(String username){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String connectQuery = String.format("DELETE FROM user_member WHERE username = '%s'",username);
        try{
            PreparedStatement pst = connectDB.prepareStatement(connectQuery);
            pst.executeUpdate();
            System.out.println("Success!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
