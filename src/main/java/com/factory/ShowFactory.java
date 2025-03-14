package com.factory;

import com.model.Show;

public class ShowFactory {
    public static Show createShow(String name, String genre) {
        return new Show(name, genre);
    }
}
