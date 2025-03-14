package com.model;

import com.observer.Observer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ShowSlot {
    private String time;
    private int totalSeats;
    private List<Booking> bookings;
    private Queue<Booking> waitlist;
    private Observer observer;

    public ShowSlot(String time, int totalSeats) {
        this.time = time;
        this.totalSeats = totalSeats;
        this.bookings = new ArrayList<>();
        this.waitlist = new LinkedList<>();
    }

    public String getTime() {
        return time;
    }

    public int gettotalSeats() {
        return totalSeats;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public Queue<Booking> getWaitlist() {
        return waitlist;
    }

    public boolean hastotalSeats(int seats) {
        int bookedSeats = bookings.stream().mapToInt(Booking::gettotalTickets).sum();
        return (bookedSeats + seats) <= totalSeats;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
        this.totalSeats -= booking.gettotalTickets();
    }

    public void updateSlots(int seats) {
        this.totalSeats += seats;
    }

    public void addToWaitlist(Booking booking) {
        waitlist.offer(booking);
    }

    public void registerObserver(Observer observer) {
        this.observer = observer;
    }

    public void removeObserver(Observer observer) {
        this.observer = null;
    }

    public void notifyObserver(String message) {
        if (observer != null) {
            observer.update(message);
        }
    }
}
