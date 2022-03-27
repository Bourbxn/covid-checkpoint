package com.example.javafx_covid19_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class AfterLogin {

    @FXML
    private Button logout;


    public void userLogout(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("Login.fxml");

    }
}
