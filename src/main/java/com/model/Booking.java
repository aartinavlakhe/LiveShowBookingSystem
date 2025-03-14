package com.model;

import com.constant.Constant;

public class Booking {
    private Long id;
    private String userName;
    private String showName;
    private String timeSlot;
    private int totalTickets;

    public Booking(String userName, String showName, String timeSlot, int totalTickets) {
        this.id = Constant.id++;
        this.userName = userName;
        this.showName = showName;
        this.timeSlot = timeSlot;
        this.totalTickets = totalTickets;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getShowName() {
        return showName;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public int gettotalTickets() {
        return totalTickets;
    }
}
