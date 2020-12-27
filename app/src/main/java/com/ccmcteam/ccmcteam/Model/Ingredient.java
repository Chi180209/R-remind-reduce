package com.ccmcteam.ccmcteam.Model;

public class Ingredient {

    private String IngName;
    private String IngAmount;
    private String IngUnit;

    public Ingredient() {
    }

    public Ingredient(String ingName, String ingAmount, String ingUnit) {
        IngName = ingName;
        IngAmount = ingAmount;
        IngUnit = ingUnit;
    }

    public String getIngName() {
        return IngName;
    }

    public void setIngName(String ingName) {
        IngName = ingName;
    }

    public String getIngAmount() {
        return IngAmount;
    }

    public void setIngAmount(String ingAmount) {
        IngAmount = ingAmount;
    }

    public String getIngUnit() {
        return IngUnit;
    }

    public void setIngUnit(String ingUnit) {
        IngUnit = ingUnit;
    }
}
