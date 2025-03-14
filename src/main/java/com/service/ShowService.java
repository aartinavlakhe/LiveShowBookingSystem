package com.service;

import java.util.List;

public interface ShowService {
    String registerShow(String name, String genre);
    String onboardShowSlots(String showName, String[] slotDetails);
    List<String> showAvailByGenre(String genre);
}
