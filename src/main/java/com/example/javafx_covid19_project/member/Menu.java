package com.example.javafx_covid19_project.member;

import com.example.javafx_covid19_project.Main;
import com.example.javafx_covid19_project.Pages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class Menu extends Pages {

    @FXML private Button check_point_btn;
    @FXML private Button add_timeline_btn;
    @FXML private Button logout_btn;


    public void goToAddTimeline(ActionEvent event) throws IOException{
        Main m = new Main();
        AddTimeline addTimeline = new AddTimeline();
        m.changeScenePassValue("AddTimeline.fxml",addTimeline,getUserLoggedIn());
        System.out.println("Go to AddTimeline with "+getUserLoggedIn());
    }

    public void goToCheckPoint(ActionEvent event) throws IOException{
        Main m = new Main();
        CheckPoint checkPoint = new CheckPoint();
        m.changeScenePassValue("CheckPoint.fxml",checkPoint,getUserLoggedIn());
        System.out.println("Go to CheckPoint with "+getUserLoggedIn());
    }


    public void userLogout(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("Login.fxml");
    }

}
