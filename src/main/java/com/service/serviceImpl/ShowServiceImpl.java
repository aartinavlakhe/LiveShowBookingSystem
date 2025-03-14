package com.service.serviceImpl;

import com.factory.ShowFactory;
import com.model.Show;
import com.model.ShowSlot;
import com.repository.ShowRepository;
import com.service.ShowService;
import com.strategy.RankingStrategy;

import java.util.ArrayList;
import java.util.List;

public class ShowServiceImpl implements ShowService {

    private ShowRepository showRepository;
    private RankingStrategy rankingStrategy;

    public ShowServiceImpl(ShowRepository showRepository, RankingStrategy rankingStrategy) {
        this.showRepository = showRepository;
        this.rankingStrategy = rankingStrategy;
    }

    public String registerShow(String name, String genre) {
        if (showRepository.getShow(name) != null) {
            return "Show with this name already exists!";
        }
        showRepository.addShow(ShowFactory.createShow(name, genre));
        return name + " show is registered!";
    }

    public String onboardShowSlots(String showName, String[] slotDetails) {
        Show show = showRepository.getShow(showName);
        if (show == null) return "Show not found!";

        for (String slotDetail : slotDetails) {
            String[] parts = slotDetail.split(" ");
            if (parts.length != 2) return "Invalid input format! Expected: TIME CAPACITY";

            String time = parts[0].trim();
            int capacity = Integer.parseInt(parts[1].trim());

            if (show.hasSlot(time)) {
                return "Slot " + time + " already exists for this show!";
            }

            if (!isValidOneHourSlot(time)) {  // Ensure 1-hour slots only
                return "Sorry, show timings are of 1 hour only";
            }

            show.addSlot(new ShowSlot(time, capacity));
        }
        return "Slots added for " + showName;
    }

    public List<String> showAvailByGenre(String genre) {
        List<String> availableShows = new ArrayList<>();

        for (Show show : showRepository.getAllShows().values()) {
            if (show.getGenre().equalsIgnoreCase(genre)) {
                for (ShowSlot slot : show.getSlots()) {
                    availableShows.add(show.getName() + ": (" + slot.getTime() + ") " + slot.gettotalSeats());
                }
            }
        }
        return availableShows.isEmpty() ? List.of("No available shows for this genre!") : rankingStrategy.rank(availableShows);
    }

    private boolean isValidOneHourSlot(String time) {
        String[] startEnd = time.split("-");
        if (startEnd.length != 2) return false;

        int startHour = Integer.parseInt(startEnd[0].split(":")[0]);
        int endHour = Integer.parseInt(startEnd[1].split(":")[0]);

        return (endHour - startHour == 1);
    }
}
