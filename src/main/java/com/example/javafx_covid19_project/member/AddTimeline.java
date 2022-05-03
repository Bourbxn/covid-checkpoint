package com.example.javafx_covid19_project.member;

import com.example.javafx_covid19_project.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import java.net.URL;
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
    @FXML private Button get_time_btn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void autoInitialize() {

    }

    // Time start control button
    @FXML
    private void hourStartUp(){
        controlTimeUp(hour_start_txt,23);
    }

    @FXML
    private void hourStartDown(){
        controlTimeDown(hour_start_txt,23);
    }

    @FXML
    private void minStartUp(){
        controlTimeUp(min_start_txt,59);
    }

    @FXML
    private void minStartDown(){
        controlTimeDown(min_start_txt,59);
        //controlTimeUp(minStart,0,min_start_txt);
    }

    // Time end control button
    @FXML
    private void hourEndUp(){
        controlTimeUp(hour_end_txt,23);
    }

    @FXML
    private void hourEndDown(){
        controlTimeDown(hour_end_txt,23);
    }

    @FXML
    private void minEndUp(){
        controlTimeUp(min_end_txt,59);
    }

    @FXML
    private void minEndDown(){
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

    @FXML
    private void getTime(ActionEvent event){
        System.out.println(hour_start_txt.getText()+":"+min_start_txt.getText());
        System.out.println(hour_end_txt.getText()+":"+min_end_txt.getText());
        System.out.println(location_tf.getText());
        System.out.println(sickness_cb.isSelected());
    }
}
