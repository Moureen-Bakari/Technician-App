package com.george.optica_technicianapplication.Models;

public class Notification {
    private String orderNumber;
    private String date;
    private String timeStarted;
    private String timeOrderSent;
    private String timeStopped;

    public Notification(String orderNumber, String date, String timeStarted, String timeOrderSent, String timeStopped) {
        this.orderNumber = orderNumber;
        this.date = date;
        this.timeStarted = timeStarted;
        this.timeOrderSent = timeOrderSent;
        this.timeStopped = timeStopped;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeStarted() {
        return timeStarted;
    }

    public void setTimeStarted(String timeStarted) {
        this.timeStarted = timeStarted;
    }

    public String getTimeOrderSent() {
        return timeOrderSent;
    }

    public void setTimeOrderSent(String timeOrderSent) {
        this.timeOrderSent = timeOrderSent;
    }

    public String getTimeStopped() {
        return timeStopped;
    }

    public void setTimeStopped(String timeStopped) {
        this.timeStopped = timeStopped;
    }
}







































