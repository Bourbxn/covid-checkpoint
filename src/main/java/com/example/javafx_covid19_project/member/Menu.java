package com.example.javafx_covid19_project.member;

import com.example.javafx_covid19_project.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Menu extends Pages implements Initializable, AutoInitialize {
    @FXML private Button check_point_btn;
    @FXML private Button add_timeline_btn;
    @FXML private Button logout_btn;
    @FXML private Button my_covid_timeline_btn;
    @FXML private Text first_name_txt;
    @FXML private Text last_name_txt;
    @FXML private Text age_txt;
    @FXML private Text gender_txt;
    @FXML private Text vaccine_txt;
    @FXML private Text covid_round_txt;
    @FXML private AnchorPane bg_app;
    @FXML private TextField first_name_edit_tf;
    @FXML private Button first_name_edit_btn;
    @FXML private TextField last_name_edit_tf;
    @FXML private Button last_name_edit_btn;
    @FXML private TextField age_edit_tf;
    @FXML private Button age_edit_btn;
    @FXML private ComboBox<String> gender_edit_cbb;
    @FXML private Button gender_edit_btn;
    @FXML private ComboBox<String> vaccine_edit_cbb;
    @FXML private Button vaccine_edit_btn;
    public String firstNameInfo,lastNameInfo,ageInfo,genderInfo,vaccineInfo,covidRoundInfo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gender_edit_cbb.setItems(FXCollections.observableArrayList("Male","Female","Other"));
        vaccine_edit_cbb.setItems(FXCollections.observableArrayList("0","1","2","3","4","5","6","7","8"));
        first_name_edit_tf.setVisible(false);
        last_name_edit_tf.setVisible(false);
        age_edit_tf.setVisible(false);
        gender_edit_cbb.setVisible(false);
        vaccine_edit_cbb.setVisible(false);
    }

    @Override
    public void autoInitialize(){
        setDataDB();
    }

    private void setDataDB(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String connectQuery = String.format("SELECT first_name, last_name, age, gender, vaccine, covid_round FROM user_member WHERE username = '%s'",getUserLoggedIn());
        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            while (queryOutput.next()){
                this.firstNameInfo = queryOutput.getString("first_name");
                this.lastNameInfo = queryOutput.getString("last_name");
                this.ageInfo = queryOutput.getString("age");
                this.genderInfo = queryOutput.getString("gender");
                this.vaccineInfo = queryOutput.getString("vaccine");
                this.covidRoundInfo = queryOutput.getString("covid_round");
            }
            first_name_txt.setText(firstNameInfo);
            last_name_txt.setText(lastNameInfo);
            age_txt.setText(ageInfo);
            gender_txt.setText(genderInfo);
            vaccine_txt.setText(vaccineInfo);
            covid_round_txt.setText(covidRoundInfo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showEditFirstName(ActionEvent event){
        first_name_edit_tf.setVisible(true);
    }

    public void hideEditFirstName(ActionEvent event){
        if(!checkSQLInjection(first_name_edit_tf.getText())){
            saveEditToDB("first_name",first_name_edit_tf.getText());
            first_name_edit_tf.setVisible(false);
            first_name_txt.setText(first_name_edit_tf.getText());
        }
    }

    public void showEditLastName(ActionEvent event){
        last_name_edit_tf.setVisible(true);
    }

    public void hideEditLastName(ActionEvent event){
        if(!checkSQLInjection(last_name_edit_tf.getText())){
            saveEditToDB("last_name",last_name_edit_tf.getText());
            last_name_edit_tf.setVisible(false);
            last_name_txt.setText(last_name_edit_tf.getText());
        }
    }

    public void showEditAge(ActionEvent event){
        age_edit_tf.setVisible(true);
    }

    public void hideEditAge(ActionEvent event){
        if(!checkSQLInjection(age_edit_tf.getText()) || !checkAgeContains(age_edit_tf.getText())){
            saveEditToDB("age",age_edit_tf.getText());
            age_edit_tf.setVisible(false);
            age_txt.setText(age_edit_tf.getText());
        }

    }

    public void showEditGender(ActionEvent event){
        gender_edit_cbb.setVisible(true);
    }

    public void hideEditGender(ActionEvent event){
        saveEditToDB("gender",gender_edit_cbb.getValue());
        gender_edit_cbb.setVisible(false);
        gender_txt.setText(gender_edit_cbb.getValue());
    }

    public void showEditVaccine(ActionEvent event){
        vaccine_edit_cbb.setVisible(true);
    }

    public void hideEditVaccine(ActionEvent event){
        saveEditToDB("vaccine",vaccine_edit_cbb.getValue());
        vaccine_edit_cbb.setVisible(false);
        vaccine_txt.setText(vaccine_edit_cbb.getValue());
    }

    private void saveEditToDB(String dataDB, String value){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        try{
            String connectQuery = String.format("UPDATE user_member SET %s = ? WHERE username = '%s'",dataDB,getUserLoggedIn());
            PreparedStatement pst = connectDB.prepareStatement(connectQuery);
            pst.setString(1, value);
            pst.executeUpdate();
            System.out.println("Edit new covid round success!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean checkAgeContains(String age){
        return age.matches("[0-9]+");
    }
}
