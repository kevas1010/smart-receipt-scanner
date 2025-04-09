package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecipeSelectionFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> allRecipes;
    private DatabaseHelper databaseHelper;
    private List<FoodOwned> ownedFoods;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_selection, container, false);

        databaseHelper = new DatabaseHelper(getContext());
        allRecipes = databaseHelper.getAllRecipes();
        ownedFoods = databaseHelper.getAllFoodsOwned();

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Button findRecipesButton = view.findViewById(R.id.find_recipes_button);
        findRecipesButton.setOnClickListener(v -> findRecipes());

        return view;
    }

    private void findRecipes() {
        // Get the nutritional goals from the MacroCalculatorFragment
        Bundle args = getArguments();
        if (args != null) {
            float targetCalories = args.getFloat("targetCalories", 0);
            float targetProtein = args.getFloat("targetProtein", 0);
            float targetCarbs = args.getFloat("targetCarbs", 0);
            float targetFats = args.getFloat("targetFats", 0);

            Recipe bestMatch = null;
            Recipe closeMatch = null;
            float bestMatchDifference = Float.MAX_VALUE;
            float closeMatchDifference = Float.MAX_VALUE;

            for (Recipe recipe : allRecipes) {
                List<RecipeIngredient> ingredients = databaseHelper.getAllRecipeIngredients(recipe.getRecipeID());
                float totalCalories = 0;
                float totalProtein = 0;
                float totalCarbs = 0;
                float totalFats = 0;
                boolean canMake = true;

                for (RecipeIngredient ingredient : ingredients) {
                    Food food = databaseHelper.getFoodById(ingredient.getFoodID());
                    if(food!=null){
                        totalCalories += food.getCalories() * ingredient.getQuantity();
                        totalProtein += food.getProteinContent() * ingredient.getQuantity();
                        totalCarbs += food.getCarbohydrateContent() * ingredient.getQuantity();
                        totalFats += food.getFatContent() * ingredient.getQuantity();
                    }
                    // Check if the ingredient is owned
                    if (!isFoodOwned(ingredient.getFoodID(), ownedFoods, ingredient.getQuantity())) {
                        canMake = false;
                    }
                }

                // Calculate differences for matching
                float calorieDifference = Math.abs(totalCalories - targetCalories);
                float proteinDifference = Math.abs(totalProtein - targetProtein);
                float carbsDifference = Math.abs(totalCarbs - targetCarbs);
                float fatsDifference = Math.abs(totalFats - targetFats);
                float totalDifference = calorieDifference + proteinDifference + carbsDifference + fatsDifference;

                // Check for the best match (can be made with owned foods)
                if (canMake && totalDifference < bestMatchDifference) {
                    bestMatch = recipe;
                    bestMatchDifference = totalDifference;
                }

                // Check for a close match (may not be able to be made with owned foods)
                if (!canMake && totalDifference < closeMatchDifference) {
                    closeMatch = recipe;
                    closeMatchDifference = totalDifference;
                }
            }

            // Prepare the adapter with the best match and close match
            List<Recipe> suggestedRecipes = new ArrayList<>();
            if (bestMatch != null) {
                suggestedRecipes.add(bestMatch);
            } else {
                Toast.makeText(getContext(), "No recipes can be made with owned foods.", Toast.LENGTH_SHORT).show();
            }
            if (closeMatch != null) {
                suggestedRecipes.add(closeMatch);
            }

            // Update the RecyclerView with suggested recipes
            recipeAdapter = new RecipeAdapter(suggestedRecipes);
            recyclerView.setAdapter(recipeAdapter);
        }
    }

    private boolean isFoodOwned(int foodID, List<FoodOwned> ownedFoods, float requiredQuantity) {
        for (FoodOwned foodOwned : ownedFoods) {
            if (foodOwned.getFoodID() == foodID && foodOwned.getQuantity() >= requiredQuantity) {
                return true;
            }
        }
        return false;
    }
}
