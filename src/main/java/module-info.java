module com.example.javafx_covid19_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires org.controlsfx.controls;


    opens com.example.javafx_covid19_project to javafx.fxml;
    exports com.example.javafx_covid19_project;
    exports com.example.javafx_covid19_project.admin;
    opens com.example.javafx_covid19_project.admin to javafx.fxml;
    exports com.example.javafx_covid19_project.member;
    opens com.example.javafx_covid19_project.member to javafx.fxml;
    exports com.example.javafx_covid19_project.staff;
    opens com.example.javafx_covid19_project.staff to javafx.fxml;
}