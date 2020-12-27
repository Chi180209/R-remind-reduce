package com.ccmcteam.ccmcteam.Model;

import java.util.ArrayList;

public class RecipeGroup {
    private String categoryTitle;
    private ArrayList<RecipeData> listRecipe;

    public RecipeGroup() {
    }

    public RecipeGroup(String categoryTitle, ArrayList<RecipeData> listRecipe) {
        this.categoryTitle = categoryTitle;
        this.listRecipe = listRecipe;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public ArrayList<RecipeData> getListRecipe() {
        return listRecipe;
    }

    public void setListRecipe(ArrayList<RecipeData> listRecipe) {
        this.listRecipe = listRecipe;
    }
}
