package com.example.myapplication;

public class RecipeIngredient {
    private int recipeIngredientID; // Unique ID for the ingredient in the recipe
    private int recipeID;            // ID of the recipe
    private int foodID;              // ID of the food item
    private float quantity;           // Quantity of the food item used in the recipe

    // Constructor
    public RecipeIngredient(int foodID, float quantity) {
        this.recipeIngredientID = recipeIngredientID;
        this.recipeID = recipeID;
        this.foodID = foodID;
        this.quantity = quantity;
    }

    // Getters
    public int getRecipeIngredientID() {
        return recipeIngredientID;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public int getFoodID() {
        return foodID;
    }

    public float getQuantity() {
        return quantity;
    }

    // Setters
    public void setRecipeIngredientID(int recipeIngredientID) {
        this.recipeIngredientID = recipeIngredientID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
}
