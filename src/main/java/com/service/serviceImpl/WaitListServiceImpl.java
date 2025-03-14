package com.service.serviceImpl;

import com.factory.BookingFactory;
import com.model.Booking;
import com.model.Show;
import com.model.ShowSlot;
import com.model.User;
import com.repository.ShowRepository;
import com.repository.UserRepository;
import com.service.WaitListService;

public class WaitListServiceImpl implements WaitListService {
    private final ShowRepository showRepository;
    private final UserRepository userRepository;

    public WaitListServiceImpl(ShowRepository showRepository, UserRepository userRepository) {
        this.showRepository = showRepository;
        this.userRepository = userRepository;
    }

    public String addToWaitlist(String userName, String showName, String time, int numPersons) {
        Show show = showRepository.getShow(showName);
        if (show == null) return "Show not found!";

        for (ShowSlot slot : show.getSlots()) {
            if (slot.getTime().split("-")[0].equals(time)) {
                slot.addToWaitlist(BookingFactory.createBooking(userName, showName, time, numPersons));
                return "Added to waitlist!";
            }
        }
        return "Invalid time slot!";
    }

    public void processWaitlist(String showName, String time, int totalTicket) {
        Show show = showRepository.getShow(showName);
        if (show == null) return;

        for (ShowSlot slot : show.getSlots()) {
            if (slot.getTime().equals(time)) {
                while (!slot.getWaitlist().isEmpty() && slot.hastotalSeats(totalTicket)) {
                    Booking waitlistedBooking = slot.getWaitlist().peek();
                    if (waitlistedBooking != null && slot.hastotalSeats(waitlistedBooking.gettotalTickets())) {
                        slot.addBooking(waitlistedBooking);
                        userRepository.addUserBooking(waitlistedBooking.getUserName(), waitlistedBooking);
                        User user = userRepository.getUser(waitlistedBooking.getUserName());
                        if (user != null) {
                            slot.registerObserver(user);
                            slot.notifyObserver("Your waitlisted ticket for show '" + show.getName() + "' at " + slot.getTime() + " has been confirmed!");
                            slot.removeObserver(user);
                        }
                        slot.getWaitlist().poll();
                    }
                    else
                        break;
                }
            }
        }
    }
}
