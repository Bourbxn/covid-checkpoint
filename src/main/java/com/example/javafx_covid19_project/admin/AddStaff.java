package com.example.javafx_covid19_project.admin;

import com.example.javafx_covid19_project.AutoInitialize;
import com.example.javafx_covid19_project.DatabaseConnection;
import com.example.javafx_covid19_project.Main;
import com.example.javafx_covid19_project.Pages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class AddStaff extends Pages implements AutoInitialize, Initializable {
    @FXML private TextField username_staff;
    @FXML private TextField password_staff;
    @FXML private TextField confirm_password_staff;
    @FXML private Button create_staff_btn;
    @FXML private Button go_back_menu_btn;
    @FXML private Rectangle password_check_1;
    @FXML private Rectangle password_check_2;
    @FXML private Rectangle password_check_3;
    @FXML private Text password_check_txt;
    @FXML private Text password_match_txt;
    @FXML private TextField username_border_staff;
    @FXML private TextField password_border_staff;
    @FXML private TextField confirm_password_border_staff;
    @FXML private Text username_txt_staff;
    @FXML private Text password_txt_staff;
    @FXML private Text confirm_password_txt_staff;
    @FXML private BorderPane bg_app;
    @FXML private Text already_taken_txt;
    private int passwordStrenghtLv;
    private boolean alreadyUsernameUsed;

    @Override
    public void autoInitialize() {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        already_taken_txt.setVisible(false);
    }

    public void staffCreateAccount(ActionEvent event)throws IOException{
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        checkInvalidTextfield(connectDB);
        bg_app.requestFocus();
        System.out.println("confirm " + getConfirmRegister());
        if(getConfirmRegister()){
            System.out.println("success add staff");
            createAccount(connectDB);
        }

    }

    public void goBackMenu(ActionEvent event) throws IOException{
        Main m = new Main();
        m.changeScene("MenuAdmin.fxml");
        System.out.println("go back menu");
    }

    private void createAccount(Connection connectDB) throws IOException {
        Main m = new Main();
        try{
            String connectQuery = "INSERT INTO user (username, password, role) VALUES (?,?,?)";
            PreparedStatement pst = connectDB.prepareStatement(connectQuery);
            pst.setString(1, username_staff.getText());
            pst.setString(2, password_staff.getText());
            pst.setString(3, "STAFF");
            pst.executeUpdate();
            System.out.println("Success!");
            m.changeScene("StaffManagement.fxml");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void getUsernameUsed(Connection connectDB){
        String connectQuery = String.format("SELECT * FROM user WHERE username = '%s'",username_staff.getText());
        try{
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(connectQuery);
            if (resultSet.next()) {
                alreadyUsernameUsed = true;
            }
            else {
                alreadyUsernameUsed = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean getConfirmRegister(){
        return !(checkInvalidInputTF(username_staff) || checkUsernameContains(username_staff.getText()) || alreadyUsernameUsed
                || checkInvalidInputTF(password_staff) || passwordStrenghtLv < 2
                || !confirm_password_staff.getText().equals(password_staff.getText()));
    }

    private void checkInvalidTextfield(Connection connectDB){
        getUsernameUsed(connectDB);
        //username
        if(checkInvalidInputTF(username_staff) || checkUsernameContains(username_staff.getText())){
            setInvalidFocus(username_border_staff,username_txt_staff);
        }
        if(alreadyUsernameUsed){
            already_taken_txt.setVisible(true);
        }
        //password
        if(checkInvalidInputTF(password_staff) || passwordStrenghtLv < 2){
            setInvalidFocus(password_border_staff,password_txt_staff);
        }
        //confirm password
        if( checkInvalidInputTF(confirm_password_staff)|| !confirm_password_staff.getText().equals(password_staff.getText())){
            setInvalidFocus(confirm_password_border_staff,confirm_password_txt_staff);
        }
    }

    private boolean checkInvalidInputTF(TextField input){
        return input.getText().equals("");
    }

    private boolean checkUsernameContains(String username){
        return username.length()<8;
    }

    private void setInvalidFocus(TextField textField, Text text){
        clearFocus();
        bg_app.requestFocus();
        textField.setBorder(Border.stroke(Color.rgb(255,0,0)));
        text.setFill(Color.INDIANRED);
    }

    @FXML
    private void checkStrenghtPasswordAddStaff() {
        String password = password_staff.getText();
        int n = password.length();
        boolean hasLower = false, hasUpper = false, hasDigit = false, specialChar = false;
        Set<Character> set = new HashSet<Character>(Arrays.asList('!', '@', '#', '$', '%', '^', '&','*', '(', ')', '-', '+'));
        for (char i : password.toCharArray())
        {
            if (Character.isLowerCase(i))
                hasLower = true;
            if (Character.isUpperCase(i))
                hasUpper = true;
            if (Character.isDigit(i))
                hasDigit = true;
            if (set.contains(i))
                specialChar = true;
        }

        if(password.equals("")){
            password_check_1.setFill(Color.rgb(228,228,228));
            password_check_2.setFill(Color.rgb(228,228,228));
            password_check_3.setFill(Color.rgb(228,228,228));
            password_check_txt.setText("No");
            password_check_txt.setFill(Color.rgb(61,61,61));
            passwordStrenghtLv = 0;
        }
        else if (hasDigit && hasLower && hasUpper && specialChar && (n >= 8)){
            password_check_1.setFill(Color.FORESTGREEN);
            password_check_2.setFill(Color.FORESTGREEN);
            password_check_3.setFill(Color.FORESTGREEN);
            password_check_txt.setText("Strong");
            password_check_txt.setFill(Color.FORESTGREEN);
            passwordStrenghtLv = 3;
        }
        else if ((hasLower || hasUpper || specialChar) && (n >= 6)){
            password_check_1.setFill(Color.rgb(255,174,0));
            password_check_2.setFill(Color.rgb(255,174,0));
            password_check_3.setFill(Color.rgb(228,228,228));
            password_check_txt.setText("Moderate");
            password_check_txt.setFill(Color.rgb(255,174,0));
            passwordStrenghtLv = 2;
        }
        else{
            password_check_1.setFill(Color.INDIANRED);
            password_check_2.setFill(Color.rgb(228,228,228));
            password_check_3.setFill(Color.rgb(228,228,228));
            password_check_txt.setText("Weak");
            password_check_txt.setFill(Color.INDIANRED);
            passwordStrenghtLv = 1;
        }
    }

    @FXML
    private void checkMatchPassword(){
        if(password_staff.getText().equals("")){
            password_check_txt.setFill(Color.rgb(61,61,61));
            password_check_txt.setText("No");
        }
        else if(password_staff.getText().equals(confirm_password_staff.getText())){
            password_match_txt.setText("Yes");
            password_match_txt.setFill(Color.FORESTGREEN);
        }
        else{
            password_match_txt.setText("No");
            password_match_txt.setFill(Color.INDIANRED);
        }
    }

    @FXML
    private void usernameRegFocus(){
        setTextFocus(username_txt_staff,username_border_staff);
        setTextfieldFocus(username_staff,password_staff,confirm_password_staff);
    }

    @FXML
    private void passwordRegFocus(){
        setTextFocus(password_txt_staff,password_border_staff);
        setTextfieldFocus(password_staff,username_staff,confirm_password_staff);
    }

    @FXML
    private void confirmPasswordRegFocus(){
        setTextFocus(confirm_password_txt_staff,confirm_password_border_staff);
        setTextfieldFocus(confirm_password_staff,username_staff,password_staff);
    }

    private void setTextFocus(Text text,TextField textField){
        text.setFill(Color.rgb(77,77,77));
        textField.setBorder(Border.stroke(Color.rgb(77,77,77)));
    }

    private void setTextfieldFocus(Control focus, Control use1, Control use2){
        focus.setBorder(Border.stroke(Color.rgb(56,77,108)));
        use1.setBorder(Border.EMPTY);
        use2.setBorder(Border.EMPTY);
    }

    private void clearFocus(){
        username_staff.setBorder(Border.EMPTY);
        password_staff.setBorder(Border.EMPTY);
        confirm_password_staff.setBorder(Border.EMPTY);
    }
}
