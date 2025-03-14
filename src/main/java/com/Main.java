package com;

import com.repository.ShowRepository;
import com.repository.UserRepository;
import com.service.CancelationService;
import com.service.UserService;
import com.service.WaitListService;
import com.service.serviceImpl.*;
import com.strategy.RankingStrategy;
import com.strategy.strategyImpl.StartTimeRankingStrategy;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ShowRepository showRepo = new ShowRepository();
        UserRepository userRepo = new UserRepository();

        RankingStrategy rankingStrategy = new StartTimeRankingStrategy();
        ShowServiceImpl showService = new ShowServiceImpl(showRepo, rankingStrategy);
        WaitListService waitService = new WaitListServiceImpl(showRepo,userRepo);
        CancelationService cancelingService = new CancelationServiceImpl(showRepo,userRepo, waitService);
        BookingServiceImpl bookingService = new BookingServiceImpl(showRepo, userRepo, waitService);
        UserService userService = new UserServiceImpl(userRepo);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter command:");
            String command = scanner.nextLine();
            String[] parts = command.split(" ");

            switch (parts[0]) {
                case "REGISTER_SHOW":
                    System.out.println(showService.registerShow(parts[1], parts[2]));
                    break;
                case "ONBOARD_SLOT":
                    String[] slots = command.substring(command.indexOf(parts[2])).split(", ");
                    System.out.println(showService.onboardShowSlots(parts[1], slots));
                    break;
                case "SHOW_AVAILABLE":
                    System.out.println(showService.showAvailByGenre(parts[1]));
                    break;
                case "BOOK_TICKET":
                    System.out.println(bookingService.bookShow(parts[1], parts[2], parts[3], Integer.parseInt(parts[4])));
                    break;
                case "CANCEL_BOOKING":
                    System.out.println(cancelingService.cancelBooking(Long.valueOf(parts[1])));
                    break;
                case "VIEW_USER_BOOKINGS":
                    System.out.println(userService.viewUserBookings(parts[1]));
                    break;
                case "EXIT":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid command");
            }
        }

    }
}