package com.service.serviceImpl;

import com.model.Booking;
import com.model.Show;
import com.model.ShowSlot;
import com.repository.ShowRepository;
import com.repository.UserRepository;
import com.service.CancelationService;
import com.service.WaitListService;

import java.util.Iterator;

public class CancelationServiceImpl implements CancelationService {
    private ShowRepository showRepository;
    private UserRepository userRepository;

    private WaitListService waitListService;

    public CancelationServiceImpl(ShowRepository showRepository, UserRepository userRepository, WaitListService waitListService) {
        this.showRepository = showRepository;
        this.userRepository = userRepository;
        this.waitListService = waitListService;
    }

    public String cancelBooking(Long bookingId) {
        for (Show show : showRepository.getAllShows().values()) {
            for (ShowSlot slot : show.getSlots()) {
                Iterator<Booking> iterator = slot.getBookings().iterator();
                while (iterator.hasNext()) {
                    Booking booking = iterator.next();
                    if (booking.getId().equals(bookingId)) {
                        int totalTicket = booking.gettotalTickets();
                        iterator.remove();
                        slot.updateSlots(slot.gettotalSeats() + totalTicket);
                        userRepository.getUser(booking.getUserName()).cancelBooking(bookingId);
                        waitListService.processWaitlist(show.getName(), slot.getTime(), totalTicket);

                        if (show.getSlots().isEmpty()) {
                            showRepository.getAllShows().remove(show.getName());
                        }
                        return "Booking canceled!";
                    }
                }
            }
        }
        return "Booking ID not found!";
    }
}
