package com.ccmcteam.ccmcteam.Model;

public class Recipe {
    private String Title;
    private int Img;
    private String Category;
    private String TimeCook;
    private String InstructionCook;

    public Recipe() {
    }

    public Recipe(String title, int img, String category, String timeCook, String instructionCook) {
        this.Title = title;
        this.Img = img;
        this.Category = category;
        this.TimeCook = timeCook;
        this.InstructionCook = instructionCook;
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getImg() {
        return Img;
    }

    public void setImg(int img) {
        Img = img;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getTimeCook() {
        return TimeCook;
    }

    public void setTimeCook(String timeCook) {
        TimeCook = timeCook;
    }

    public String getInstructionCook() {
        return InstructionCook;
    }

    public void setInstructionCook(String instructionCook) {
        InstructionCook = instructionCook;
    }
}
