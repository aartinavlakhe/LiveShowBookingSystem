package com.factory;

import com.model.Booking;

public class BookingFactory {
    public static Booking createBooking(String userName, String showName, String time, int totalSeats) {
        return new Booking(userName, showName, time, totalSeats);
    }
}
