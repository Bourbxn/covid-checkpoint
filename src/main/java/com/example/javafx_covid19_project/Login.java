package com.example.javafx_covid19_project;

import com.example.javafx_covid19_project.member.Menu;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Login implements Initializable{
    @FXML private TextField username_tf;
    @FXML private PasswordField password_pf;
    @FXML private Button login_btn;
    @FXML private Hyperlink create_account_hp;
    @FXML private Line username_line;
    @FXML private Line password_line;
    @FXML private ImageView login_hover_btn;
    @FXML private BorderPane bg_app;
    @FXML private Text error_login_txt;

    private String role;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_hover_btn.setVisible(false);
        error_login_txt.setVisible(false);
    }

    public void userLoginMain() throws IOException {
        clearEffect();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        setRoleDB(connectDB);
        checkLogin(connectDB);
    }

    private void clearEffect(){
        bg_app.requestFocus();
        username_line.setStroke(Color.BLACK);
        password_line.setStroke(Color.BLACK);
    }

    private void checkLogin(Connection connectDB) throws IOException {
        Main m = new Main();

        String connectQuery = String.format("SELECT * FROM user WHERE username = '%s' AND password = '%s'"
                ,username_tf.getText(),password_pf.getText());

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            if(!queryOutput.isBeforeFirst()){
                error_login_txt.setVisible(true);
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

    public void changeUsernameLine(){
        password_line.setStroke(Color.rgb(0,0,0));
        username_line.setStroke(Color.rgb(0,137,150));
    }

    public void changeBackUsernameLine(){
        username_line.setStroke(Color.rgb(0,0,0));
    }

    public void changePasswordLine(){
        username_line.setStroke(Color.rgb(0,0,0));
        password_line.setStroke(Color.rgb(0,137,150));
    }

    public void changeButtonColor(){
        login_hover_btn.setVisible(true);
    }

    public void changeBackButtonColor(){
        login_hover_btn.setVisible(false);
    }
}
