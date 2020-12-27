package com.ccmcteam.ccmcteam.Model.Firebase;

public class Items {
    private String category;
    private String name;
    private String amount;
    private String unit;
    private String expDate;
    private String image;
    private String ingredientId;
    private String before;

    public Items() {
    }

    public Items(String amount,String before, String category, String expDate, String image, String ingredientId, String name, String unit) {
        this.category = category;
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.expDate = expDate;
        this.image = image;
        this.ingredientId = ingredientId;
        this.before = before;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }
}
