package com.example.javafx_covid19_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Menu extends Pages{

    @FXML
    private Button add_timeline_btn;
    @FXML
    private Button logout_btn;


    public void goToAddTimeline(ActionEvent event) throws IOException{
        Main m = new Main();
        AddTimeline addTimeline = new AddTimeline();
        m.changeScenePassValue("AddTimeline.fxml",addTimeline,getUserLoggedIn());
        System.out.println("Go to AddTimeline with "+getUserLoggedIn());
    }


    public void userLogout(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("Login.fxml");
    }

}
