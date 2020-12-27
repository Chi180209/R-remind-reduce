package com.ccmcteam.ccmcteam.Model;

public class IngredientView {
    private String ing_name, exp_date, ing_category, storage_unit;
    private int storage_amount, until_exp_date, img_ingredient;

    //private boolean expanded;

    public IngredientView() {
    }

    public IngredientView(String ing_name, String exp_date, String ing_category, String storage_unit, int storage_amount, int until_exp_date, int img_ingredient) {
        this.ing_name = ing_name;
        this.exp_date = exp_date;
        this.ing_category = ing_category;
        this.storage_unit = storage_unit;
        this.storage_amount = storage_amount;
        this.until_exp_date = until_exp_date;
        this.img_ingredient = img_ingredient;

        //this.expanded = false;
    }

    public String getIng_name() {
        return ing_name;
    }

    public void setIng_name(String ing_name) {
        this.ing_name = ing_name;
    }

    public String getExp_date() {
        return exp_date;
    }

    public void setExp_date(String exp_date) {
        this.exp_date = exp_date;
    }

    public String getIng_category() {
        return ing_category;
    }

    public void setIng_category(String ing_category) {
        this.ing_category = ing_category;
    }

    public String getStorage_unit() {
        return storage_unit;
    }

    public void setStorage_unit(String storage_unit) {
        this.storage_unit = storage_unit;
    }

    public int getStorage_amount() {
        return storage_amount;
    }

    public void setStorage_amount(int storage_amount) {
        this.storage_amount = storage_amount;
    }

    public int getUntil_exp_date() {
        return until_exp_date;
    }

    public void setUntil_exp_date(int until_exp_date) {
        this.until_exp_date = until_exp_date;
    }

    public int getImg_ingredient() {
        return img_ingredient;
    }

    public void setImg_ingredient(int img_ingredient) {
        this.img_ingredient = img_ingredient;
    }

    /*
    public boolean isExpanded(){
        return expanded;
    }
    public void setExpanded(boolean expanded){
        this.expanded = expanded;
    }

     */


}
