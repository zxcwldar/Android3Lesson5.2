package com.example.android3lesson2.models.viewpager_model;

public class ViewPagerModel {
    private String title;
    private String description;
    private int image;

    public ViewPagerModel(String title, String description, int image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }


}
