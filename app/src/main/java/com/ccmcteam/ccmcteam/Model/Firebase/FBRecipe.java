package com.ccmcteam.ccmcteam.Model.Firebase;

public class FBRecipe {

    String recipeId;
    String recipeName;
    String timeCook;
    String recipeCategory;
    String recipeImage;
    String recipeHowto;

    public FBRecipe() {
    }



    public FBRecipe(String recipeId, String recipeName, String timeCook, String recipeCategory, String recipeImage, String recipeHowto) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.timeCook = timeCook;
        this.recipeCategory = recipeCategory;
        this.recipeImage = recipeImage;
        this.recipeHowto = recipeHowto;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getTimeCook() {
        return timeCook;
    }

    public void setTimeCook(String timeCook) {
        this.timeCook = timeCook;
    }

    public String getRecipeCategory() {
        return recipeCategory;
    }

    public void setRecipeCategory(String recipeCategory) {
        this.recipeCategory = recipeCategory;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    public String getRecipeHowto() {
        return recipeHowto;
    }

    public void setRecipeHowto(String recipeHowto) {
        this.recipeHowto = recipeHowto;
    }
}
