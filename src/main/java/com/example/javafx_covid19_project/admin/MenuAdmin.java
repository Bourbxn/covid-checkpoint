package com.example.javafx_covid19_project.admin;

import com.example.javafx_covid19_project.Main;
import com.example.javafx_covid19_project.Pages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class MenuAdmin extends Pages {
    @FXML Button add_staff_btn;
    @FXML Button logout_btn;
    @FXML Button staff_manage_btn;
    @FXML Button member_manage_btn;

    public void goToAddStaff(ActionEvent event) throws IOException{
        Main m = new Main();
        m.changeScene("AddStaff.fxml");
        System.out.println("go to add staff");
    }

    public void logoutAdmin(ActionEvent event) throws IOException{
        Main m = new Main();
        m.changeScene("Login.fxml");
        System.out.println("go to login");
    }

    public void goStaffManage(ActionEvent event) throws IOException{
        Main m = new Main();
        m.changeScene("StaffManagement.fxml");
    }

    public void goMemberManage(ActionEvent event) throws IOException{
        Main m = new Main();
        m.changeScene("MemberManagement.fxml");
    }
}
