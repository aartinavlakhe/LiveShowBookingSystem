package com.model;

import com.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class User implements Observer {
    private String name;
    private List<Booking> showBookings;

    public User(String name) {
        this.name = name;
        this.showBookings = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Booking> getshowBookings() {
        return showBookings;
    }

    public void addBooking(Booking booking) {
        showBookings.add(booking);
    }

    public void cancelBooking(Long bookingId) {
        showBookings.removeIf(b -> b.getId().equals(bookingId));
    }

    public boolean hasBookingAtTime(String time) {
        return showBookings.stream().anyMatch(booking -> booking.getTimeSlot().equals(time));
    }

    @Override
    public void update(String message) {
        System.out.println("Notification for " + name + ": " + message);
    }
}
