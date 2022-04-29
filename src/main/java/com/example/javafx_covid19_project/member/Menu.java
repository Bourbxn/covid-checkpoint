package com.example.javafx_covid19_project.member;

import com.example.javafx_covid19_project.AddCovidTimeline;
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
    @FXML private Button info_btn;
    @FXML private Button my_covid_timeline_btn;


    public void goToAddTimeline(ActionEvent event) throws IOException{
//        Main m = new Main();
//        AddTimeline addTimeline = new AddTimeline();
//        m.changeScenePassValue("AddTimeline.fxml",addTimeline,getUserLoggedIn());
//        System.out.println("Go to AddTimeline with "+getUserLoggedIn());
        Main m = new Main();
        AddCovidTimeline addCovidTimeline = new AddCovidTimeline();
        m.changeScenePassValue("AddCovidTimeline.fxml",addCovidTimeline,getUserLoggedIn());
    }

    public void goToCheckPoint(ActionEvent event) throws IOException{
        Main m = new Main();
        Pages checkPoint = new CheckPoint();
        m.changeScenePassValue("CheckPoint.fxml",checkPoint,getUserLoggedIn());
        System.out.println("Go to CheckPoint with " + getUserLoggedIn());
    }

    public void goToInfo(ActionEvent event) throws IOException{
        Main m = new Main();
        Pages infoMember = new InfoMember();
        m.changeScenePassValue("InfoMember.fxml",infoMember,getUserLoggedIn());
        System.out.println("Go to Info with " + getUserLoggedIn());
    }

    public void goToMyCovidTimeline(ActionEvent event) throws IOException{
        Main m = new Main();
        Pages myCovidTimeline = new MyCovidTimeline();
        m.changeScenePassValue("MyCovidTimeline.fxml",myCovidTimeline,getUserLoggedIn());
        System.out.println("Go to my covid timeline with " + getUserLoggedIn());
    }


    public void userLogout(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("Login.fxml");
    }

}
