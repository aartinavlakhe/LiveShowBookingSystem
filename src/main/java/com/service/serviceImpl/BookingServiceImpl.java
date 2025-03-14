package com.service.serviceImpl;

import com.factory.BookingFactory;
import com.model.Booking;
import com.model.Show;
import com.model.ShowSlot;

import com.model.User;
import com.repository.ShowRepository;
import com.repository.UserRepository;
import com.service.BookingService;
import com.service.WaitListService;

public class BookingServiceImpl implements BookingService {

    private ShowRepository showRepository;
    private UserRepository userRepository;

    private WaitListService waitListService;

    public BookingServiceImpl(ShowRepository showRepository, UserRepository userRepository, WaitListService waitListService) {
        this.showRepository = showRepository;
        this.userRepository = userRepository;
        this.waitListService = waitListService;
    }

    public String bookShow(String userName, String showName, String time, int totalSeats) {
        Show show = showRepository.getShow(showName);
        if (show == null) {
            return "Show not found!";
        }

        User user = userRepository.getUser(userName);
        if (user != null && user.hasBookingAtTime(time)) {
            return "You already have a booking at this time!";
        }

        ShowSlot selectedSlot = show.getSlots().stream()
                .filter(slot -> slot.getTime().split("-")[0].equals(time))
                .findFirst()
                .orElse(null);

        if (selectedSlot == null) return "Time slot not available!";

        if (!selectedSlot.hastotalSeats(totalSeats)) {
            return waitListService.addToWaitlist(userName, showName, time, totalSeats);
        }

        Booking booking = BookingFactory.createBooking(userName, showName, time, totalSeats);
        selectedSlot.addBooking(booking);
        userRepository.addUserBooking(userName, booking);

        return "Booked. Booking ID: " + booking.getId();
    }


}
