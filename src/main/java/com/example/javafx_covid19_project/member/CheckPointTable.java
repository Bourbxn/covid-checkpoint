package com.example.javafx_covid19_project.member;

public class CheckPointTable {
    private String date;
    private String timeStart;
    private String timeEnd;
    private String sickness;

    public CheckPointTable(String date, String timeStart, String timeEnd, String sickness) {
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.sickness = sickness;
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

    public String getSickness() {
        return sickness;
    }

    public void setSickness(String sickness) {
        this.sickness = sickness;
    }
}
