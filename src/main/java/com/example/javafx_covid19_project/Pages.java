package com.example.javafx_covid19_project;

import com.example.javafx_covid19_project.member.*;
import javafx.event.ActionEvent;

import java.io.IOException;

public class Pages {
    private String username;

    public Pages(){}

    public void setUserLoggedIn(String username){
        this.username = username;
    }

    public String getUserLoggedIn(){
        return username;
    }

    public String getUserPage(String role, String adminPages,String staffPages, String memberPages){
        return switch (role) {
            case "ADMIN" -> adminPages;
            case "STAFF" -> staffPages;
            case "MEMBER" -> memberPages;
            default -> null;
        };
    }

    public void goToProfile(ActionEvent event) throws IOException{
        Main m = new Main();
        Menu menu = new Menu();
        m.changeScenePassValue("Menu.fxml",menu,getUserLoggedIn());
        System.out.println("Go to Profile with "+getUserLoggedIn());
    }

    public void goToAddTimeline(ActionEvent event) throws IOException {
        Main m = new Main();
        AddTimeline addTimeline = new AddTimeline();
        m.changeScenePassValue("AddTimeline.fxml",addTimeline,getUserLoggedIn());
        System.out.println("Go to AddTimeline with "+getUserLoggedIn());
    }

    public void goToCheckPoint(ActionEvent event) throws IOException{
        Main m = new Main();
        Pages checkPoint = new CheckPoint();
        m.changeScenePassValue("CheckPoint.fxml",checkPoint,getUserLoggedIn());
        System.out.println("Go to CheckPoint with " + getUserLoggedIn());
    }


    public void goToMyCovidTimeline(ActionEvent event) throws IOException{
        Main m = new Main();
        Pages myCovidTimeline = new MyCovidTimeline();
        m.changeScenePassValue("MyCovidTimeline.fxml",myCovidTimeline,getUserLoggedIn());
        System.out.println("Go to my covid timeline with " + getUserLoggedIn());
    }


    public void userLogoutMenu(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("Login.fxml");
    }
}
