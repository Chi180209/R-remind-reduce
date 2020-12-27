package com.ccmcteam.ccmcteam.Model.Firebase;

public class FBIngredientOfRecipe {
    String idRc;
    String id;
    String ingName;
    String ingAmount;
    String ingUnit;

    public FBIngredientOfRecipe(String idRc, String id, String ingName, String ingAmount, String ingUnit) {
        this.idRc = idRc;
        this.id = id;
        this.ingName = ingName;
        this.ingAmount = ingAmount;
        this.ingUnit = ingUnit;
    }

    public FBIngredientOfRecipe() {
    }

    public String getIdRc() {
        return idRc;
    }

    public void setIdRc(String idRc) {
        this.idRc = idRc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIngName() {
        return ingName;
    }

    public void setIngName(String ingName) {
        this.ingName = ingName;
    }

    public String getIngAmount() {
        return ingAmount;
    }

    public void setIngAmount(String ingAmount) {
        this.ingAmount = ingAmount;
    }

    public String getIngUnit() {
        return ingUnit;
    }

    public void setIngUnit(String ingUnit) {
        this.ingUnit = ingUnit;
    }
}
