package com.ccmcteam.ccmcteam.Model;

public class RecipeData {
    private String name, image;

    public RecipeData() {
    }

    public RecipeData(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

