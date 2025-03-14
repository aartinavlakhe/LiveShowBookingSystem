package com.service;

public interface WaitListService {
    String addToWaitlist(String userName, String showName, String time, int numPersons);
    void processWaitlist(String showName, String time, int totalTicket);
}
