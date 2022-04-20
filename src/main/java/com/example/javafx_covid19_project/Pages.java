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

    public String getUserPage(String role, String adminPages,String staffPages, String memberPages){
        return switch (role) {
            case "ADMIN" -> adminPages;
            case "STAFF" -> staffPages;
            case "MEMBER" -> memberPages;
            default -> null;
        };
    }
}
