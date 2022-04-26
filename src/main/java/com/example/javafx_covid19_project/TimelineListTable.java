package com.example.javafx_covid19_project;

import com.example.javafx_covid19_project.staff.TimelineList;

public class TimelineListTable {
    private String date;
    private String timeStart;
    private String timeEnd;
    private String name;
    private String gender;
    private String age;
    private String location;

    public TimelineListTable(){

    }

    public TimelineListTable(String location, String date,String timeStart,String timeEnd,String name,String gender,String age){
        this.location = location;
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
