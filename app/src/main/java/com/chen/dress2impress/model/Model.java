package com.chen.dress2impress.model;

import java.util.LinkedList;
import java.util.List;

public class Model {
    public static final Model instance = new Model();

    List<Outfit> data = new LinkedList<>();

    private Model() {
        for (int i = 0; i < 10; i++) {
            data.add(new Outfit("" + i, "title" + i, "description" + i, "url" + i));
        }
    }

    public List<Outfit> getAllOutfits() {
        return data;
    }

    public Outfit getOutfit(String id) {
        return null;
    }

    public void updateOutfit(Outfit outfit) {

    }

    public void deleteOutfit(Outfit outfit) {

    }
}
