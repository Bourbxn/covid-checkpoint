package com.example.javafx_covid19_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
    private static Stage stg;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stg = primaryStage;
        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Covid-19 Checkpoint");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }

    public void changeScenePassValue(String fxml,Pages pages,String value) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        Pages page = loader.getController();
        page.setUserLoggedIn(value);
        stg.getScene().setRoot(root);
    }

    public void goToISAGWeb(){
        getHostServices().showDocument("https://isag-lab.github.io/web/");
    }

    public static void main(String[] args) {
        launch(args);
    }
}

//Test github slack