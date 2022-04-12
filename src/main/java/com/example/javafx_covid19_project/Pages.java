package com.example.javafx_covid19_project;

public class Pages {
    private String username;

    public Pages(){}

    public void setUserLoggedIn(String username){
        this.username = username;
    }

    public String getUserLoggedIn(){
        return username;
    }
}
