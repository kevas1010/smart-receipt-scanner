package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;

public class MacroCalculatorFragment extends Fragment {

    private DatabaseHelper dbHelper;
    private TextView tvCalories, tvProtein, tvFats, tvCarbs;
    private Button btnCalculateMacros;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_macro_calculator, container, false);

        dbHelper = new DatabaseHelper(getContext());

        tvCalories = view.findViewById(R.id.tv_calories);
        tvProtein = view.findViewById(R.id.tv_protein);
        tvFats = view.findViewById(R.id.tv_fats);
        tvCarbs = view.findViewById(R.id.tv_carbs);
        btnCalculateMacros = view.findViewById(R.id.btn_calculate_macros);

        btnCalculateMacros.setOnClickListener(v -> calculateMacros());

        return view;
    }

    private void calculateMacros() {
        Cursor userInfoCursor = dbHelper.getUserInfoCursor();
        Cursor eventCursor = dbHelper.getEventsWithinAWeek();

        if (userInfoCursor != null && userInfoCursor.moveToFirst()) {
            // Safely retrieve column indexes
            int weightColumnIndex = userInfoCursor.getColumnIndex("Weight");
            int heightColumnIndex = userInfoCursor.getColumnIndex("Height");
            int ageColumnIndex = userInfoCursor.getColumnIndex("Age");
            int genderColumnIndex = userInfoCursor.getColumnIndex("Gender");

            // Check if the columns exist
            if (weightColumnIndex >= 0 && heightColumnIndex >= 0 && ageColumnIndex >= 0 && genderColumnIndex >= 0) {
                // Safely retrieve data
                int weight = userInfoCursor.getInt(weightColumnIndex);
                int height = userInfoCursor.getInt(heightColumnIndex);
                int age = userInfoCursor.getInt(ageColumnIndex);
                String gender = userInfoCursor.getString(genderColumnIndex);

                // Log retrieved values for debugging
                Log.d("UserInfo", "Weight: " + weight + ", Height: " + height + ", Age: " + age + ", Gender: " + gender);

                // Calculate BMR and macros
                double BMR = calculateBMR(gender, weight, height, age);
                double totalCalories = calculateTotalCalories(BMR, eventCursor);

                double proteinIntake = calculateProtein(weight, eventCursor);
                double fatIntake = calculateFat(totalCalories, eventCursor);
                double carbsIntake = calculateCarbs(weight, eventCursor);

                // Display results
                tvCalories.setText("Calories: " + String.format("%.2f", totalCalories));
                tvProtein.setText("Protein: " + String.format("%.2f", proteinIntake) + " g");
                tvFats.setText("Fats: " + String.format("%.2f", fatIntake) + " g");
                tvCarbs.setText("Carbs: " + String.format("%.2f", carbsIntake) + " g");

            } else {
                Toast.makeText(getContext(), "Error: Missing required user information.", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getContext(), "User info is missing!", Toast.LENGTH_SHORT).show();
        }

        if (userInfoCursor != null) userInfoCursor.close();
        if (eventCursor != null) eventCursor.close();
    }

    private double calculateBMR(String gender, int weight, int height, int age) {
        if (gender.equalsIgnoreCase("Male")) {
            return 10 * weight + 6.25 * height - 5 * age + 5;
        } else {
            return 10 * weight + 6.25 * height - 5 * age - 161;
        }
    }

    private double calculateTotalCalories(double BMR, Cursor eventCursor) {
        double totalCalories = BMR;
        if (eventCursor != null) {
            while (eventCursor.moveToNext()) {
                int difficultyColumnIndex = eventCursor.getColumnIndex("ExertionLevel");
                int eventDateColumnIndex = eventCursor.getColumnIndex("EventDate");

                // Check for valid column indexes
                if (difficultyColumnIndex >= 0 && eventDateColumnIndex >= 0) {
                    int difficulty = eventCursor.getInt(difficultyColumnIndex);
                    int daysTillEvent = calculateDaysTillEvent(eventCursor.getString(eventDateColumnIndex));

                    totalCalories *= 1 + ((difficulty / 10.0) / daysTillEvent);
                }
            }
        }
        return totalCalories;
    }

    private double calculateProtein(int weight, Cursor eventCursor) {
        double protein = weight * 1.2;
        if (eventCursor != null) {
            while (eventCursor.moveToNext()) {
                int difficultyColumnIndex = eventCursor.getColumnIndex("ExertionLevel");
                int eventDateColumnIndex = eventCursor.getColumnIndex("EventDate");

                // Check for valid column indexes
                if (difficultyColumnIndex >= 0 && eventDateColumnIndex >= 0) {
                    int difficulty = eventCursor.getInt(difficultyColumnIndex);
                    int daysTillEvent = calculateDaysTillEvent(eventCursor.getString(eventDateColumnIndex));

                    protein *= 1 + ((difficulty / 10.0) / daysTillEvent);
                }
            }
        }
        return protein;
    }

    private double calculateFat(double totalCalories, Cursor eventCursor) {
        double fat = totalCalories * 0.2;
        if (eventCursor != null) {
            while (eventCursor.moveToNext()) {
                int difficultyColumnIndex = eventCursor.getColumnIndex("ExertionLevel");
                int eventDateColumnIndex = eventCursor.getColumnIndex("EventDate");

                // Check for valid column indexes
                if (difficultyColumnIndex >= 0 && eventDateColumnIndex >= 0) {
                    int difficulty = eventCursor.getInt(difficultyColumnIndex);
                    int daysTillEvent = calculateDaysTillEvent(eventCursor.getString(eventDateColumnIndex));

                    fat *= 1 + ((difficulty / 10.0) / daysTillEvent);
                }
            }
        }
        return fat;
    }

    private double calculateCarbs(int weight, Cursor eventCursor) {
        double carbs = weight * 5;
        if (eventCursor != null) {
            while (eventCursor.moveToNext()) {
                int difficultyColumnIndex = eventCursor.getColumnIndex("ExertionLevel");
                int eventDateColumnIndex = eventCursor.getColumnIndex("EventDate");

                // Check for valid column indexes
                if (difficultyColumnIndex >= 0 && eventDateColumnIndex >= 0) {
                    int difficulty = eventCursor.getInt(difficultyColumnIndex);
                    int daysTillEvent = calculateDaysTillEvent(eventCursor.getString(eventDateColumnIndex));

                    carbs *= 1 + ((difficulty / 10.0) / daysTillEvent);
                }
            }
        }
        return carbs;
    }

    private int calculateDaysTillEvent(String eventDate) {
        // Implement logic to calculate the number of days until the event based on eventDate
        // For simplicity, you can use a Date library to parse and calculate the difference
        return 1;  // Return 1 as a placeholder
    }
}
