package com.model;

import java.util.*;

public class Show {
    private String name;
    private String genre;
    private List<ShowSlot> slots;
    private int showBooked;

    public Show(String name, String genre) {
        this.name = name;
        this.genre = genre;
        this.slots = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<ShowSlot> getSlots() {
        return slots;
    }

    public String getGenre() {
        return genre;
    }

    public void addSlot(ShowSlot slot) {
        this.slots.add(slot);
    }

    public boolean hasSlot(String time) {
        return slots.stream().anyMatch(slot -> slot.getTime().equals(time));
    }

    public int getShowBooked() {
        return showBooked;
    }

    public void showBooked() {
        showBooked++;
    }
}

