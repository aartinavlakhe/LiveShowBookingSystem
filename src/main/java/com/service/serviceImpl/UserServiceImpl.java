package com.service.serviceImpl;

import com.model.Booking;
import com.model.User;
import com.repository.UserRepository;
import com.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<String> viewUserBookings(String userName) {
        User user = userRepository.getUser(userName);
        if (user == null || user.getshowBookings().isEmpty()) return List.of("No bookings found!");

        List<String> bookingsList = new ArrayList<>();
        for (Booking booking : user.getshowBookings()) {
            bookingsList.add("Booking ID: " + booking.getId() + " | Show: " + booking.getShowName() +
                    " | Time: " + booking.getTimeSlot() + " | Tickets: " + booking.gettotalTickets());
        }
        return bookingsList;
    }
}
