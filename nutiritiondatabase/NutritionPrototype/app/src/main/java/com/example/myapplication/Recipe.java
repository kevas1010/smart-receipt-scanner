package com.example.myapplication;

public class Recipe {
    private int recipeID;
    private String recipeName;
    private String instructions;
    private int prepTime;
    private int cookTime;
    private double totalProtein;

    public Recipe(int recipeID, String recipeName, String instructions, int prepTime, int cookTime, double totalProtein) {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.instructions = instructions;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.totalProtein = totalProtein;
    }


    public int getRecipeID() { return recipeID; }
    public void setRecipeID(int recipeID) { this.recipeID = recipeID; }

    public String getRecipeName() { return recipeName; }
    public void setRecipeName(String recipeName) { this.recipeName = recipeName; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public int getPrepTime() { return prepTime; }
    public void setPrepTime(int prepTime) { this.prepTime = prepTime; }

    public int getCookTime() { return cookTime; }
    public void setCookTime(int cookTime) { this.cookTime = cookTime; }

    public double getTotalProtein() { return totalProtein; }
    public void setTotalProtein(double totalProtein) { this.totalProtein = totalProtein; }
}
