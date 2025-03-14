package com.repository;

import com.model.Booking;
import com.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private Map<String, User> users;

    public UserRepository() {
        this.users = new HashMap<>();
    }

    public void addUserBooking(String userName, Booking booking) {
        users.putIfAbsent(userName, new User(userName));
        users.get(userName).addBooking(booking);
    }

    public User getUser(String userName) {
        return users.get(userName);
    }
}
