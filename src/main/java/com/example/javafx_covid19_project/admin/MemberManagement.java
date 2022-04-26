package com.example.javafx_covid19_project.admin;

import com.example.javafx_covid19_project.DatabaseConnection;
import com.example.javafx_covid19_project.Main;
import com.example.javafx_covid19_project.MemberListTable;
import com.example.javafx_covid19_project.Pages;
import com.example.javafx_covid19_project.staff.TimelineListTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class MemberManagement extends Pages implements Initializable {
    @FXML Button back_to_menu_btn;
    @FXML Button remove_member_btn;
    @FXML private TableView<MemberListTable> member_tb;
    @FXML private TableColumn<MemberListTable, String> col_username;
    @FXML private TableColumn<MemberListTable, String> col_name;
    @FXML private TableColumn<MemberListTable, String> col_gender;
    @FXML private TableColumn<MemberListTable, String> col_age;
    @FXML private TableColumn<MemberListTable, String> col_round;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        addMemberList();
    }

    private void addMemberList() {
        ObservableList<MemberListTable> timelineListTable = getTimelineListTable();
        col_username.setCellValueFactory(new PropertyValueFactory<MemberListTable,String>("username"));
        col_name.setCellValueFactory(new PropertyValueFactory<MemberListTable,String>("name"));
        col_gender.setCellValueFactory(new PropertyValueFactory<MemberListTable,String>("gender"));
        col_age.setCellValueFactory(new PropertyValueFactory<MemberListTable,String>("age"));
        col_round.setCellValueFactory(new PropertyValueFactory<MemberListTable,String>("covidRound"));
        member_tb.setItems(timelineListTable);
    }

    private ObservableList<MemberListTable> getTimelineListTable(){
        ObservableList<MemberListTable> timelineList = FXCollections.observableArrayList();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String connectQuery = "SELECT username, first_name, last_name, gender, age, covid_round FROM user_member ORDER BY username";
        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            while (queryOutput.next()){
                String username = queryOutput.getString("username");
                String name = queryOutput.getString("first_name") + "  " + queryOutput.getString("last_name");
                String gender = queryOutput.getString("gender");
                String age = queryOutput.getString("age");
                String covidRound = queryOutput.getString("covid_round");
                timelineList.add(new MemberListTable(username,name,gender,age,covidRound));
            }
            System.out.println("Success!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timelineList;
    }

    public void removeMember(ActionEvent event) throws IOException{
        ObservableList<MemberListTable> memberListTables = member_tb.getSelectionModel().getSelectedItems();
        int selectedID = member_tb.getSelectionModel().getSelectedIndex();
        String memberName = member_tb.getSelectionModel().getSelectedItem().getUsername();
        member_tb.getItems().remove(selectedID);
        MemberListTable memberListTable = new MemberListTable();
        removeMemberDB(memberName);
    }

    private void removeMemberDB(String username){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String connectQuery = String.format("DELETE FROM user, user_member, timeline_covid WHERE username = '%s'",username);
        try{
            PreparedStatement pst = connectDB.prepareStatement(connectQuery);
            pst.executeUpdate();
            System.out.println("Success!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void backMenuMemberManage(ActionEvent event) throws IOException{
        Main m = new Main();
        m.changeScene("MenuAdmin.fxml");
    }

}
