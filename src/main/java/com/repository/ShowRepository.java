package com.repository;

import com.model.Show;

import java.util.HashMap;
import java.util.Map;

public class ShowRepository {
    private Map<String, Show> shows;

    public ShowRepository() {
        this.shows = new HashMap<>();
    }

    public void addShow(Show show) {
        shows.put(show.getName(), show);
    }

    public Show getShow(String name) {
        return shows.get(name);
    }

    public Map<String, Show> getAllShows() {
        return shows;
    }
}
