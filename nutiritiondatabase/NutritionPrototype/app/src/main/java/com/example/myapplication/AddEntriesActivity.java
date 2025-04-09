package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddEntriesActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;

    EditText foodNameInput, caloriesInput, proteinInput, fatInput, carbInput;
    EditText foodOwnedIDInput, quantityInput, purchaseDateInput, expirationDateInput, storageLocationInput;
    EditText recipeNameInput, instructionsInput, prepTimeInput, cookTimeInput, totalProteinInput;
    EditText eventNameInput, eventDateInput, exertionLevelInput, lengthOfEventInput, descriptionInput;

    // New ingredient input fields
    EditText additionalIngredientInput1, additionalIngredientInput2, additionalIngredientInput3;

    Button addFoodButton, addFoodOwnedButton, addRecipeButton, addEventButton, addIngredientButton, saveRecipeButton;

    LinearLayout ingredientsLayout; // Layout for ingredients input
    List<RecipeIngredient> ingredientsList = new ArrayList<>(); // List to store ingredients

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entries);

        dbHelper = new DatabaseHelper(this);

        // Food section
        foodNameInput = findViewById(R.id.foodNameInput);
        caloriesInput = findViewById(R.id.caloriesInput);
        proteinInput = findViewById(R.id.proteinInput);
        fatInput = findViewById(R.id.fatInput);
        carbInput = findViewById(R.id.carbInput);
        addFoodButton = findViewById(R.id.addFoodButton);

        // Food Owned section
        foodOwnedIDInput = findViewById(R.id.foodOwnedIDInput);
        quantityInput = findViewById(R.id.quantityInput);
        purchaseDateInput = findViewById(R.id.purchaseDateInput);
        expirationDateInput = findViewById(R.id.expirationDateInput);
        storageLocationInput = findViewById(R.id.storageLocationInput);
        addFoodOwnedButton = findViewById(R.id.addFoodOwnedButton);

        // Recipe section
        recipeNameInput = findViewById(R.id.recipeNameInput);
        instructionsInput = findViewById(R.id.instructionsInput);
        prepTimeInput = findViewById(R.id.prepTimeInput);
        cookTimeInput = findViewById(R.id.cookTimeInput);
        addRecipeButton = findViewById(R.id.addRecipeButton);

        // New ingredient inputs
        additionalIngredientInput1 = findViewById(R.id.additionalIngredientInputone);
        additionalIngredientInput2 = findViewById(R.id.additionalIngredientInputtwo);
        additionalIngredientInput3 = findViewById(R.id.additionalIngredientInputthree);

        // Layout for ingredients
        ingredientsLayout = findViewById(R.id.ingredientsLayout);
        addIngredientButton = findViewById(R.id.addIngredientButton); // Button to add ingredients
        saveRecipeButton = findViewById(R.id.saveRecipeButton); // Button to save the recipe

        // Button actions
        addFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodName = foodNameInput.getText().toString();
                int calories = Integer.parseInt(caloriesInput.getText().toString());
                float protein = Float.parseFloat(proteinInput.getText().toString());
                float fat = Float.parseFloat(fatInput.getText().toString());
                float carbs = Float.parseFloat(carbInput.getText().toString());

                dbHelper.addFood(foodName, calories, protein, fat, carbs);
                Toast.makeText(AddEntriesActivity.this, "Food added", Toast.LENGTH_SHORT).show();
            }
        });

        addFoodOwnedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int foodID = Integer.parseInt(foodOwnedIDInput.getText().toString());
                int quantity = Integer.parseInt(quantityInput.getText().toString());
                String purchaseDate = purchaseDateInput.getText().toString();
                String expirationDate = expirationDateInput.getText().toString();
                String storageLocation = storageLocationInput.getText().toString();

                dbHelper.addFoodOwned(foodID, quantity, purchaseDate, expirationDate, storageLocation);
                Toast.makeText(AddEntriesActivity.this, "Food Owned added", Toast.LENGTH_SHORT).show();
            }
        });

        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipeName = recipeNameInput.getText().toString();
                String instructions = instructionsInput.getText().toString();
                int prepTime = Integer.parseInt(prepTimeInput.getText().toString());
                int cookTime = Integer.parseInt(cookTimeInput.getText().toString());

                dbHelper.addRecipe(recipeName, instructions, prepTime, cookTime, new ArrayList<>()); // Empty for now
                Toast.makeText(AddEntriesActivity.this, "Recipe added", Toast.LENGTH_SHORT).show();
            }
        });

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventName = eventNameInput.getText().toString();
                String eventDate = eventDateInput.getText().toString();
                String exertionLevel = exertionLevelInput.getText().toString();
                int lengthOfEvent = Integer.parseInt(lengthOfEventInput.getText().toString());
                String description = descriptionInput.getText().toString();

                dbHelper.addEvent(eventName, eventDate, exertionLevel, lengthOfEvent, description);
                Toast.makeText(AddEntriesActivity.this, "Event added", Toast.LENGTH_SHORT).show();
            }
        });

        // Button action to add ingredients
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIngredientField();
            }
        });

        // Button action to save the recipe along with ingredients
        saveRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecipeWithIngredients();
            }
        });
    }

    private void addIngredientField() {
        // Create new EditText for ingredient input
        EditText ingredientEditText = new EditText(this);
        ingredientEditText.setHint("Ingredient Name");
        ingredientsLayout.addView(ingredientEditText);

        // Create new EditText for quantity input
        EditText quantityEditText = new EditText(this);
        quantityEditText.setHint("Quantity");
        ingredientsLayout.addView(quantityEditText);
    }

    private void saveRecipeWithIngredients() {
        String recipeName = recipeNameInput.getText().toString();
        String instructions = instructionsInput.getText().toString();
        int prepTime = Integer.parseInt(prepTimeInput.getText().toString());
        int cookTime = Integer.parseInt(cookTimeInput.getText().toString());

        // Clear the previous ingredients
        ingredientsList.clear();

        // Retrieve ingredient names and quantities from the layout
        for (int i = 0; i < ingredientsLayout.getChildCount(); i += 2) {
            EditText ingredientField = (EditText) ingredientsLayout.getChildAt(i);
            EditText quantityField = (EditText) ingredientsLayout.getChildAt(i + 1);

            String ingredientName = ingredientField.getText().toString();
            String quantity = quantityField.getText().toString();

            if (!ingredientName.isEmpty() && !quantity.isEmpty()) {
                int foodID = getFoodIdFromName(ingredientName); // Look up food ID from name
                int quantityValue = Integer.parseInt(quantity);
                ingredientsList.add(new RecipeIngredient(foodID, quantityValue));
            }
        }

        // Add additional ingredients from the new fields
        addAdditionalIngredients();

        // Save the recipe along with ingredients
        dbHelper.addRecipe(recipeName, instructions, prepTime, cookTime, ingredientsList);
        Toast.makeText(AddEntriesActivity.this, "Recipe with ingredients added", Toast.LENGTH_SHORT).show();
    }

    private void addAdditionalIngredients() {
        String[] additionalIngredients = {
                additionalIngredientInput1.getText().toString(),
                additionalIngredientInput2.getText().toString(),
                additionalIngredientInput3.getText().toString()
        };

        for (String ingredient : additionalIngredients) {
            if (!ingredient.isEmpty()) {
                int foodID = getFoodIdFromName(ingredient); // Look up food ID from name
                ingredientsList.add(new RecipeIngredient(foodID, 1)); // Assuming quantity is 1 for additional ingredients
            }
        }
    }

    private int getFoodIdFromName(String ingredientName) {
        // Implement your logic to get the Food ID from ingredient name
        return 1; // Placeholder
    }
}
