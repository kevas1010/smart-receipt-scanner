package com.example.myapplication;

public class Food {
    private int foodID;
    private String foodName;
    private int calories;
    private double proteinContent;
    private double fatContent;
    private double carbohydrateContent;

    // Constructor
    public Food(int foodID, String foodName, int calories, double proteinContent, double fatContent, double carbohydrateContent) {
        this.foodID = foodID;
        this.foodName = foodName;
        this.calories = calories;
        this.proteinContent = proteinContent;
        this.fatContent = fatContent;
        this.carbohydrateContent = carbohydrateContent;
    }

    // Getters and setters
    public int getFoodID() { return foodID; }
    public void setFoodID(int foodID) { this.foodID = foodID; }

    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }

    public int getCalories() { return calories; }
    public void setCalories(int calories) { this.calories = calories; }

    public double getProteinContent() { return proteinContent; }
    public void setProteinContent(double proteinContent) { this.proteinContent = proteinContent; }

    public double getFatContent() { return fatContent; }
    public void setFatContent(double fatContent) { this.fatContent = fatContent; }

    public double getCarbohydrateContent() { return carbohydrateContent; }
    public void setCarbohydrateContent(double carbohydrateContent) { this.carbohydrateContent = carbohydrateContent; }
}
