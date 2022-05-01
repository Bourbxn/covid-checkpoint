package com.example.javafx_covid19_project;


import com.example.javafx_covid19_project.DatabaseConnection;
import com.example.javafx_covid19_project.Main;
import com.example.javafx_covid19_project.Pages;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.css.Size;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.*;

import java.io.IOException;
import java.util.*;

public class Register extends Pages implements Initializable , AutoInitialize{
    @FXML private Button button;
    @FXML private TextField first_name_register;
    @FXML private TextField last_name_register;
    @FXML private TextField age_register;
    @FXML private ComboBox<String> gender_register;
    @FXML private TextField username_register;
    @FXML private PasswordField password_register;
    @FXML private TextField confirm_password_register;
    @FXML private Hyperlink user_go_back_login;
    @FXML private Label gender_label;
    @FXML private ImageView button_img;
    @FXML private ImageView button_hover_img;
    @FXML private AnchorPane bg_app;
    @FXML private Text first_name_txt;
    @FXML private Text last_name_txt;
    @FXML private Text age_txt;
    @FXML private Text gender_txt;
    @FXML private Text username_txt;
    @FXML private Text password_txt;
    @FXML private Text confirm_password_txt;
    @FXML private TextField first_name_border_tf;
    @FXML private TextField last_name_border_tf;
    @FXML private TextField age_border_tf;
    @FXML private TextField gender_border_tf;
    @FXML private TextField username_border_tf;
    @FXML private TextField password_border_tf;
    @FXML private TextField confirm_password_border_tf;
    @FXML private Rectangle password_check_1;
    @FXML private Rectangle password_check_2;
    @FXML private Rectangle password_check_3;
    @FXML private BorderPane border_pane;
    @FXML private Text password_check_txt;
    @FXML private Text password_match_txt;
    private int passwordStrenghtLv;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gender_register.setItems(FXCollections.observableArrayList("Male","Female","Other"));
        button_hover_img.setVisible(false);
    }

    @Override
    public void autoInitialize() {
        checkStrenghtPassword();
    }

    public void userCreateAccount(ActionEvent event) throws IOException {
        checkInvalidTextfield();
        bg_app.requestFocus();
        System.out.println("confirm " + getConfirmRegister());
        if(getConfirmRegister()){
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            addToUser(connectDB);
            createMember(connectDB);
        }
    }

    private void addToUser(Connection connectDB) {
        try{
        String connectQuery = "INSERT INTO user (username, password, role) VALUES (?,?,?)";
        PreparedStatement pst = connectDB.prepareStatement(connectQuery);
        pst.setString(1, username_register.getText());
        pst.setString(2, password_register.getText());
        pst.setString(3, "MEMBER");
        pst.executeUpdate();
        System.out.println("Success!");

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void createMember(Connection connectDB) throws IOException {
        Main m = new Main();
        try{
            String connectQuery = "INSERT INTO user_member (username, first_name, last_name, age, gender, vaccine, covid_round) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pst = connectDB.prepareStatement(connectQuery);
            pst.setString(1, username_register.getText());
            pst.setString(2, first_name_register.getText());
            pst.setString(3, last_name_register.getText());
            pst.setString(4, age_register.getText());
            pst.setString(5, gender_register.getValue());
            pst.setString(6, "0");
            pst.setString(7, "0");
            pst.executeUpdate();
            System.out.println("Success!");
            m.changeScene("Login.fxml");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void checkInvalidTextfield(){
        //first name
        if(checkInvalidInputTF(first_name_register)){
            setInvalidFocus(first_name_border_tf,first_name_txt);
        }
        //last name
        if(checkInvalidInputTF(last_name_register)){
            setInvalidFocus(last_name_border_tf,last_name_txt);
        }
        //age
        if(checkInvalidInputTF(age_register) || !checkAgeContains(age_register.getText())){
            setInvalidFocus(age_border_tf,age_txt);
        }
        //gender
        if(checkInvalidInputCBB(gender_register)){
            setInvalidFocus(gender_border_tf,gender_txt);
        }
        //username
        if(checkInvalidInputTF(username_register) || checkUsernameContains(username_register.getText())){
            setInvalidFocus(username_border_tf,username_txt);
        }
        //password
        if(checkInvalidInputTF(password_register) || passwordStrenghtLv < 2){
            setInvalidFocus(password_border_tf,password_txt);
        }
        //confirm password
        if( checkInvalidInputTF(confirm_password_register)|| !confirm_password_register.getText().equals(password_register.getText())){
            setInvalidFocus(confirm_password_border_tf,confirm_password_txt);
        }
    }

    private boolean getConfirmRegister(){
        return !(checkInvalidInputTF(first_name_register) || checkInvalidInputTF(first_name_register)
                || checkInvalidInputTF(age_register) || !checkAgeContains(age_register.getText())
                || checkInvalidInputCBB(gender_register)
                || checkInvalidInputTF(username_register) || checkUsernameContains(username_register.getText())
                || checkInvalidInputTF(password_register) || passwordStrenghtLv < 2
                || !confirm_password_register.getText().equals(password_register.getText()));
    }

    private boolean checkInvalidInputTF(TextField input){
        return input.getText().equals("");
    }

    private boolean checkInvalidInputCBB(ComboBox<String> input){
        return input.getValue()==null;
    }

    private boolean checkUsernameContains(String username){
        return username.length()<8;
    }

    private boolean checkAgeContains(String age){
        return age.matches("[0-9]+");
    }

    @FXML
    private void checkStrenghtPassword(){
        String password = password_register.getText();
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

    public void checkMatchPassword(){
        if(password_register.getText().equals(confirm_password_register.getText())){
            password_match_txt.setText("Yes");
            password_match_txt.setFill(Color.FORESTGREEN);
        }
        else{
            password_match_txt.setText("No");
            password_match_txt.setFill(Color.INDIANRED);
        }
    }

    public void onPasswordTyped(){
        passwordRegFocus();
        checkStrenghtPassword();
    }

    public void userGoBackLogin(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("Login.fxml");

    }

    public void changeButtonColorReg(){
        button_hover_img.setVisible(true);
    }

    public void changeBackButtonColorReg(){
        button_hover_img.setVisible(false);
    }

    public void firstNameRegFocus(){
        setTextFocus(first_name_txt,first_name_border_tf);
        setTextfieldFocus(first_name_register,last_name_register,age_register,gender_register,username_register,password_register,confirm_password_register);
    }

    public void lastNameRegFocus(){
        setTextFocus(last_name_txt,last_name_border_tf);
        setTextfieldFocus(last_name_register,first_name_register,age_register,gender_register,username_register,password_register,confirm_password_register);
    }

    public void ageRegFocus(){
        setTextFocus(age_txt,age_border_tf);
        setTextfieldFocus(age_register,last_name_register,first_name_register,gender_register,username_register,password_register,confirm_password_register);
    }

    public void genderRegFocus(){
        setTextFocus(gender_txt,gender_border_tf);
        setTextfieldFocus(gender_register,last_name_register,age_register,first_name_register,username_register,password_register,confirm_password_register);
    }

    public void usernameRegFocus(){
        setTextFocus(username_txt,username_border_tf);
        setTextfieldFocus(username_register,last_name_register,age_register,gender_register,first_name_register,password_register,confirm_password_register);
    }

    public void passwordRegFocus(){
        setTextFocus(password_txt,password_border_tf);
        setTextfieldFocus(password_register,last_name_register,age_register,gender_register,username_register,first_name_register,confirm_password_register);
    }

    public void confirmPasswordRegFocus(){
        setTextFocus(confirm_password_txt,confirm_password_border_tf);
        setTextfieldFocus(confirm_password_register,last_name_register,age_register,gender_register,username_register,password_register,first_name_register);
    }

    private void setTextfieldFocus(Control focus, Control use1, Control use2, Control use3, Control use4, Control use5, Control use6){
        focus.setBorder(Border.stroke(Color.rgb(56,77,108)));
        use1.setBorder(Border.EMPTY);
        use2.setBorder(Border.EMPTY);
        use3.setBorder(Border.EMPTY);
        use4.setBorder(Border.EMPTY);
        use5.setBorder(Border.EMPTY);
        use6.setBorder(Border.EMPTY);
    }

    private void clearFocus(){
        first_name_register.setBorder(Border.EMPTY);
        last_name_register.setBorder(Border.EMPTY);
        age_register.setBorder(Border.EMPTY);
        gender_register.setBorder(Border.EMPTY);
        username_register.setBorder(Border.EMPTY);
        password_register.setBorder(Border.EMPTY);
        confirm_password_register.setBorder(Border.EMPTY);
    }

    private void setTextFocus(Text text,TextField textField){
        text.setFill(Color.rgb(77,77,77));
        textField.setBorder(Border.stroke(Color.rgb(77,77,77)));
    }

    private void setInvalidFocus(TextField textField, Text text){
        clearFocus();
        bg_app.requestFocus();
        textField.setBorder(Border.stroke(Color.rgb(255,0,0)));
        text.setFill(Color.INDIANRED);
    }

    public Rectangle getPasswordCheck1(){
        return password_check_1;
    }

    public Rectangle getPasswordCheck2(){
        return password_check_2;
    }

    public Rectangle getPasswordCheck3(){
        return password_check_3;
    }

    public String getPasswordBeforeCheck(){
        return password_register.getText();
    }
}
