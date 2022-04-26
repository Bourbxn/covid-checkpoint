package com.example.javafx_covid19_project;

public class MemberListTable {
    private String username,name,age,gender,covidRound;

    public MemberListTable(){}

    public MemberListTable(String username,String name,String age,String gender,String covidRound){
        this.username =username;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.covidRound = covidRound;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCovidRound() {
        return covidRound;
    }

    public void setCovidRound(String covidRound) {
        this.covidRound = covidRound;
    }
}
