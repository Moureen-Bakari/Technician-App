package com.george.optica_technicianapplication.Models;

public class WorkTimer {
    private String Date;
    private String OrderNo;
    private  String startTime;
    private String stopTime;

    //add firebase constructor
    public WorkTimer() {
    }

    public WorkTimer(String date, String orderNo, String startTime, String stopTime) {
        this.Date = date;
        this.OrderNo = orderNo;
        this.startTime = startTime;
        this.stopTime = stopTime;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }
}

