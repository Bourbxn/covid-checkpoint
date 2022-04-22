package com.example.javafx_covid19_project.admin;

import com.example.javafx_covid19_project.Main;
import com.example.javafx_covid19_project.Pages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class MenuAdmin extends Pages {
    @FXML Button add_staff_btn;

    public void goToAddStaff(ActionEvent event) throws IOException{
        Main m = new Main();
        m.changeScene("AddStaff.fxml");
        System.out.println("go to add staff");
    }
}
