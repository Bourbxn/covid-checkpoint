package com.example.javafx_covid19_project;

import com.example.javafx_covid19_project.member.Menu;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login {
    @FXML private TextField username_tf;
    @FXML private PasswordField password_pf;
    @FXML private Button login_btn;
    @FXML private Hyperlink create_account_hp;
    private String role;

    public void userLoginMain() throws IOException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        setRoleDB(connectDB);
        checkLogin(connectDB);
    }

    private void checkLogin(Connection connectDB) throws IOException {
        Main m = new Main();

        String connectQuery = String.format("SELECT * FROM user WHERE username = '%s' AND password = '%s'"
                ,username_tf.getText(),password_pf.getText());

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            if(!queryOutput.isBeforeFirst()){
                System.out.println("Failed to login!");
            }
            else{
                Menu menu = new Menu();
                String page = menu.getUserPage(role, "MenuAdmin.fxml", "MenuStaff.fxml", "Menu.fxml");
                m.changeScenePassValue(page,menu,username_tf.getText());
                System.out.println("Successful to Login!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setRoleDB(Connection connectDB) throws IOException{
        String connectQuery = String.format("SELECT role FROM user WHERE username = '%s'",username_tf.getText());
        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            while (queryOutput.next()){
                this.role = queryOutput.getString("role");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void userCreateAccountMain() throws IOException {
        Main m = new Main();
        m.changeScene("Register.fxml");
    }


}
