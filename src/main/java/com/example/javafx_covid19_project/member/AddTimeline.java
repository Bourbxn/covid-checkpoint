package com.example.javafx_covid19_project.member;

import com.example.javafx_covid19_project.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddTimeline extends Pages implements Initializable, AutoInitialize {
    private final String[] hourList = {"00","01","02","03","04","05","06","07","08","09","10","11","12",
            "13","14","15","16","17","18","19","20","21","22","23"};
    private final String[] minList = {"00","01","02","03","04","05","06","07","08","09","10","11","12",
            "13","14","15","16","17","18","19","20","21","22","23","24",
            "25","26","27","28","29","30","31","32","33","34","35","36",
            "37","38","39","40","41","42","43","44","45","46","47","48",
            "49","50","51","52","53","54","55","56","57","58","59"};

    @FXML private TableView<AddTimelineTable> add_timeline_tb;
    @FXML private TableColumn<AddTimelineTable,String> date_col;
    @FXML private TableColumn<AddTimelineTable,String> time_start_col;
    @FXML private TableColumn<AddTimelineTable,String> time_end_col;
    @FXML private TableColumn<AddTimelineTable,String> location_col;
    @FXML private TableColumn<AddTimelineTable,String> sickness_col;
    @FXML private Text hour_start_txt;
    @FXML private Text min_start_txt;
    @FXML private Text hour_end_txt;
    @FXML private Text min_end_txt;
    @FXML private TextField hour_start_tf;
    @FXML private TextField min_start_tf;
    @FXML private TextField hour_end_tf;
    @FXML private TextField min_end_tf;
    @FXML private Button hour_start_up_btn;
    @FXML private Button hour_start_down_btn;
    @FXML private Button min_start_up_btn;
    @FXML private Button min_start_down_btn;
    @FXML private Button hour_end_up_btn;
    @FXML private Button hour_end_down_btn;
    @FXML private Button min_end_up_btn;
    @FXML private Button min_end_down_btn;
    @FXML private TextField location_tf;
    @FXML private CheckBox sickness_cb;
    @FXML private DatePicker date_dpk;
    @FXML private Button add_btn;
    @FXML private Button remove_btn;
    @FXML private Button save_btn;
    private String covid_round;
    private Boolean isValidAdd;
    private String firstName;
    private String lastName;
    private String gender;
    private String age;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        date_col.setCellValueFactory(new PropertyValueFactory<AddTimelineTable,String>("date"));
        time_start_col.setCellValueFactory(new PropertyValueFactory<AddTimelineTable,String>("timeStart"));
        time_end_col.setCellValueFactory(new PropertyValueFactory<AddTimelineTable,String>("timeEnd"));
        location_col.setCellValueFactory(new PropertyValueFactory<AddTimelineTable,String>("location"));
        sickness_col.setCellValueFactory(new PropertyValueFactory<AddTimelineTable,String>("sickness"));
    }

    @Override
    public void autoInitialize() {}

    // Time start control button
    @FXML
    private void hourStartUp(){
        clearErrorTime();
        controlTimeUp(hour_start_txt,23);
    }

    @FXML
    private void hourStartDown(){
        clearErrorTime();
        controlTimeDown(hour_start_txt,23);
    }

    @FXML
    private void minStartUp(){
        clearErrorTime();
        controlTimeUp(min_start_txt,59);
    }

    @FXML
    private void minStartDown(){
        clearErrorTime();
        controlTimeDown(min_start_txt,59);
    }

    // Time end control button
    @FXML
    private void hourEndUp(){
        clearErrorTime();
        controlTimeUp(hour_end_txt,23);
    }

    @FXML
    private void hourEndDown(){
        clearErrorTime();
        controlTimeDown(hour_end_txt,23);
    }

    @FXML
    private void minEndUp(){
        clearErrorTime();
        controlTimeUp(min_end_txt,59);
    }

    @FXML
    private void minEndDown(){
        clearErrorTime();
        controlTimeDown(min_end_txt,59);
    }

    // Control time function
    private void controlTimeUp(Text text,int maxTime){
        int timeNew = Integer.parseInt(text.getText()) + 1;
        if(timeNew>maxTime){
            timeNew = 0;
        }
        text.setText(String.format("%02d",timeNew));
    }

    private void controlTimeDown(Text text, int minTime){
        int timeNew = Integer.parseInt(text.getText()) - 1;
        if(timeNew<0){
            timeNew = minTime;
        }
        text.setText(String.format("%02d",timeNew));
    }

    //Add function
    @FXML
    private void addTimeline(ActionEvent event){
        checkInvalidInput();
        if(isValidAdd){
            AddTimelineTable addTimelineTable = new AddTimelineTable(getDate(date_dpk),getTime(hour_start_txt,min_start_txt)
                    ,getTime(hour_end_txt,min_end_txt),location_tf.getText(),getSickness(sickness_cb.isSelected()));
            ObservableList<AddTimelineTable> addTimelineTables = add_timeline_tb.getItems();
            addTimelineTables.add(addTimelineTable);
            add_timeline_tb.setItems(addTimelineTables);
            clearInput();
        }
    }

    private void clearInput(){
        date_dpk.setValue(null);
        hour_start_txt.setText("00");
        min_start_txt.setText("00");
        hour_end_txt.setText("00");
        min_end_txt.setText("00");
        location_tf.clear();
        sickness_cb.setSelected(false);
    }

    private String getTime(Text hour,Text min){
        return hour.getText() + ":" + min.getText();
    }

    private String getSickness(boolean sickness){
       if(sickness){
          return "Yes";
       }
       else{
           return "No";
       }
    }

    private String getDate(DatePicker date){
        return date.getValue().toString().split("-")[2]+"/"+date.getValue().toString().split("-")[1]+"/"+date.getValue().toString().split("-")[0];
    }

    //Remove function
    @FXML
    private void removeTimeline(ActionEvent event){
        if(add_timeline_tb.getSelectionModel().getSelectedItem()!=null){
            int selectedID = add_timeline_tb.getSelectionModel().getSelectedIndex();
            add_timeline_tb.getItems().remove(selectedID);
        }
    }

    //Save function
    @FXML
    private void saveTimeline(ActionEvent event){
        if(!invalidSave()){
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            setDataDB(connectDB);
            setCovidRound(connectDB);
            addNewCovidRound(connectDB);
            AddTimelineTable addTimelineTable = new AddTimelineTable();
            List <List<String>> arrData = new ArrayList<>();
            for(int i = 0;i<add_timeline_tb.getItems().size();i++){
                addTimelineTable = add_timeline_tb.getItems().get(i);
                arrData.add(new ArrayList<>());
                arrData.get(i).add(addTimelineTable.getDate());
                arrData.get(i).add(""+addTimelineTable.getTimeStart());
                arrData.get(i).add(""+addTimelineTable.getTimeEnd());
                arrData.get(i).add(""+addTimelineTable.getLocation());
                arrData.get(i).add(""+addTimelineTable.getSickness());
            }

            for (List<String> arrIndex : arrData) {
                String date = arrIndex.get(0);
                String timeStart = arrIndex.get(1);
                String timeEnd = arrIndex.get(2);
                String location = arrIndex.get(3);
                String sickness = arrIndex.get(4);

                String dateTimeStart = datetimeToStr(date, timeStart);
                String dateTimeEnd = datetimeToStr(date, timeEnd);
                String sicknessInt = sicknessToInt(sickness);
                saveTimelineDB(getUserLoggedIn(),dateTimeStart,dateTimeEnd,location,sicknessInt,connectDB);
            }
            clearTable();
            clearInput();
        }
        else {
            add_timeline_tb.setBorder(Border.stroke(Color.INDIANRED));
        }
    }

    private boolean invalidSave(){
        return add_timeline_tb.getItems().size() < 8;
    }

    private void saveTimelineDB(String username, String datetime_start, String datetime_end,String location, String sickness,Connection connectDB){
        try{
            String connectQuery = "INSERT INTO timeline_covid (username, first_name, last_name, datetime_start, datetime_end, location, sickness, covid_round, gender, age) VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = connectDB.prepareStatement(connectQuery);
            pst.setString(1, username);
            pst.setString(2, firstName);
            pst.setString(3, lastName);
            pst.setString(4, datetime_start);
            pst.setString(5, datetime_end);
            pst.setString(6, location);
            pst.setString(7, sickness);
            pst.setString(8, covid_round);
            pst.setString(9, gender);
            pst.setString(10, age);
            pst.executeUpdate();
            System.out.println("Add timeline Success!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setCovidRound(Connection connectDB){
        String connectQuery = String.format("SELECT covid_round FROM user_member WHERE username = '%s'",getUserLoggedIn());
        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            while (queryOutput.next()){
                this.covid_round = queryOutput.getString("covid_round");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addNewCovidRound(Connection connectDB){
        covid_round = String.valueOf(Integer.parseInt(covid_round)+1);
        try{
            String connectQuery = String.format("UPDATE user_member SET covid_round = ? WHERE username = '%s'",getUserLoggedIn());
            PreparedStatement pst = connectDB.prepareStatement(connectQuery);
            pst.setString(1, covid_round);
            pst.executeUpdate();
            System.out.println("Add new covid round success!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String datetimeToStr(String date,String time){
        return date.split("/")[2] + "-" + date.split("/")[1] + "-" + date.split("/")[0] + " "
                + time + ":00";
    }

    private String sicknessToInt(String sickness){
        if(sickness.equals("Yes")){
            return "1";
        }
        else{
            return "0";
        }
    }

    private void clearTable(){
        add_timeline_tb.getItems().clear();
    }

    private void checkInvalidInput(){
        isValidAdd = true;
        if(date_dpk.getValue()==null){
            date_dpk.setBorder(Border.stroke(Color.INDIANRED));
            isValidAdd = false;
        }
        if(location_tf.getText().equals("")){
            location_tf.setBorder(Border.stroke(Color.INDIANRED));
            isValidAdd = false;
        }
        if(Integer.parseInt(hour_start_txt.getText()+min_start_txt.getText())>=Integer.parseInt(hour_end_txt.getText()+min_end_txt.getText())){
            hour_start_tf.setBorder(Border.stroke(Color.INDIANRED));
            min_start_tf.setBorder(Border.stroke(Color.INDIANRED));
            hour_end_tf.setBorder(Border.stroke(Color.INDIANRED));
            min_end_tf.setBorder(Border.stroke(Color.INDIANRED));
            isValidAdd = false;
        }
        if(!ISAG()){
            location_tf.setBorder(Border.stroke(Color.INDIANRED));
            isValidAdd = false;
        }
    }

    @FXML
    private void clearErrorLocation(){
        location_tf.setBorder(Border.EMPTY);
    }

    @FXML
    private void clearErrorDate(){
        date_dpk.setBorder(Border.EMPTY);
    }

    private void clearErrorTime(){
        hour_start_tf.setBorder(Border.EMPTY);
        min_start_tf.setBorder(Border.EMPTY);
        hour_end_tf.setBorder(Border.EMPTY);
        min_end_tf.setBorder(Border.EMPTY);
    }

    private boolean ISAG(){
        return !checkSQLInjection(location_tf.getText());
    }

    private void setDataDB(Connection connectDB){
        String connectQuery = String.format("SELECT first_name, last_name, age, gender FROM user_member WHERE username = '%s'",getUserLoggedIn());
        String[] dataDB = new String[4];
        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            while (queryOutput.next()){
                String[] columnLabel = {"first_name", "last_name", "age", "gender"};
                for(int i=0;i<4;i++) dataDB[i] = queryOutput.getString(columnLabel[i]);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        firstName = dataDB[0];
        lastName = dataDB[1];
        age = dataDB[2];
        gender = dataDB[3];
    }
}
