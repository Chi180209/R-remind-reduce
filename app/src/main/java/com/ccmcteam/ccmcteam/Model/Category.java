package com.ccmcteam.ccmcteam.Model;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;

public class Category implements Parent<Recipe> {
    private List<Recipe> mRecipeList;
    private String mCategory;

    public Category(List<Recipe> mRecipeList, String mCategory) {
        this.mRecipeList = mRecipeList;
        this.mCategory = mCategory;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    @Override
    public List<Recipe> getChildList() {
        return mRecipeList;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public Recipe getRecipe(int position){
        return mRecipeList.get(position);
    }


}
