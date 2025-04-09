package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class DisplayEntriesActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;

    TextView displayFoods, displayFoodsOwned, displayRecipes, displayRecipeIngredients, displayEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_entries);

        dbHelper = new DatabaseHelper(this);

        // Find the TextViews for each section
        displayFoods = findViewById(R.id.displayFoods);
        displayFoodsOwned = findViewById(R.id.displayFoodsOwned);
        displayRecipes = findViewById(R.id.displayRecipes);
        displayRecipeIngredients = findViewById(R.id.displayRecipeIngredients);
        displayEvents = findViewById(R.id.displayEvents);

        // Populate data
        displayFoods.setText(getFoodsData());
        displayFoodsOwned.setText(getFoodsOwnedData());
        displayRecipes.setText(getRecipesData());
        displayEvents.setText(getEventsData());
    }

    private String getFoodsData() {
        List<Food> foods = dbHelper.getAllFoods();
        StringBuilder data = new StringBuilder();
        for (Food food : foods) {
            data.append("ID: ").append(food.getFoodID())
                    .append(", Name: ").append(food.getFoodName())
                    .append(", Calories: ").append(food.getCalories())
                    .append(", Protein: ").append(food.getProteinContent())
                    .append(", Fat: ").append(food.getFatContent())
                    .append(", Carbs: ").append(food.getCarbohydrateContent())
                    .append("\n");
        }
        return data.toString();
    }

    private String getFoodsOwnedData() {
        List<FoodOwned> foodsOwned = dbHelper.getAllFoodsOwned();
        StringBuilder data = new StringBuilder();
        for (FoodOwned foodOwned : foodsOwned) {
            data.append("ID: ").append(foodOwned.getFoodOwnedID())
                    .append(", FoodID: ").append(foodOwned.getFoodID())
                    .append(", Quantity: ").append(foodOwned.getQuantity())
                    .append(", Purchase Date: ").append(foodOwned.getPurchaseDate())
                    .append(", Expiration Date: ").append(foodOwned.getExpirationDate())
                    .append(", Storage: ").append(foodOwned.getStorageLocation())
                    .append("\n");
        }
        return data.toString();
    }

    private String getRecipesData() {
        List<Recipe> recipes = dbHelper.getAllRecipes();
        StringBuilder data = new StringBuilder();
        for (Recipe recipe : recipes) {
            data.append("ID: ").append(recipe.getRecipeID())
                    .append(", Name: ").append(recipe.getRecipeName())
                    .append(", Instructions: ").append(recipe.getInstructions())
                    .append(", Prep Time: ").append(recipe.getPrepTime())
                    .append(", Cook Time: ").append(recipe.getCookTime())
                    .append(", Protein: ").append(recipe.getTotalProtein())
                    .append("\n");
        }
        return data.toString();
    }

    private String getRecipeIngredientsData(int recipeId) {
        List<RecipeIngredient> recipeIngredients = dbHelper.getAllRecipeIngredients(recipeId);
        StringBuilder data = new StringBuilder();
        for (RecipeIngredient recipeIngredient : recipeIngredients) {
            data.append("ID: ").append(recipeIngredient.getRecipeIngredientID())
                    .append(", RecipeID: ").append(recipeIngredient.getRecipeID())
                    .append(", FoodID: ").append(recipeIngredient.getFoodID())
                    .append("\n");
        }
        return data.toString();
    }

    private String getEventsData() {
        List<Event> events = dbHelper.getAllEvents();
        StringBuilder data = new StringBuilder();
        for (Event event : events) {
            data.append("ID: ").append(event.getEventID())
                    .append(", Name: ").append(event.getEventName())
                    .append(", Date: ").append(event.getEventDate())
                    .append(", Exertion: ").append(event.getExertionLevel())
                    .append(", Length: ").append(event.getLengthOfEvent())
                    .append(", Description: ").append(event.getDescription())
                    .append("\n");
        }
        return data.toString();
    }
}
