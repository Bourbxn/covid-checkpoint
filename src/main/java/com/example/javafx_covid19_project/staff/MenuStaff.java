package com.example.javafx_covid19_project.staff;

import com.example.javafx_covid19_project.Main;
import com.example.javafx_covid19_project.Pages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class MenuStaff extends Pages {
    @FXML private Button timeline_list_btn;
    @FXML private Button check_point_btn;
    @FXML private Button logout;

    public void goToTimelineList(ActionEvent event) throws IOException{
        Main m = new Main();
        m.changeScene("TimelineList.fxml");
        System.out.println("go to timeline list");
    }

    public void goToCheckPoint(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("CheckPointStaff.fxml");
        System.out.println("go to check point");
    }

    public void logoutStaff(ActionEvent event) throws IOException{
        Main m = new Main();
        m.changeScene("Login.fxml");
    }
}
