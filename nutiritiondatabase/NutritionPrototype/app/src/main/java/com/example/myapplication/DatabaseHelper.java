package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FoodApp.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FOODS_TABLE = "CREATE TABLE Foods ("
                + "FoodID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "FoodName TEXT NOT NULL,"
                + "Calories INTEGER NOT NULL,"
                + "ProteinContent REAL NOT NULL,"
                + "FatContent REAL NOT NULL,"
                + "CarbohydrateContent REAL NOT NULL);";

        String CREATE_FOODS_OWNED_TABLE = "CREATE TABLE FoodsOwned (" +
                "FoodOwnedID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "FoodID INTEGER," +
                "Quantity INTEGER NOT NULL," +
                "PurchaseDate TEXT," +
                "ExpirationDate TEXT," +
                "StorageLocation TEXT," +
                "FOREIGN KEY (FoodID) REFERENCES Foods(FoodID));";

        String CREATE_RECIPES_TABLE = "CREATE TABLE Recipes ("
                + "RecipeID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "RecipeName TEXT NOT NULL,"
                + "Instructions TEXT,"
                + "PrepTime INTEGER,"
                + "CookTime INTEGER,"
                + "TotalProtein REAL);";

        String CREATE_RECIPE_INGREDIENTS_TABLE = "CREATE TABLE RecipeIngredients ("
                + "RecipeIngredientID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "RecipeID INTEGER,"
                + "FoodID INTEGER,"
                + "Quantity REAL NOT NULL," // Store the quantity of each food in the recipe
                + "FOREIGN KEY (RecipeID) REFERENCES Recipes(RecipeID),"
                + "FOREIGN KEY (FoodID) REFERENCES Foods(FoodID));";

        String CREATE_EVENTS_TABLE = "CREATE TABLE Events (" +
                "EventID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "EventName TEXT NOT NULL," +
                "EventDate TEXT NOT NULL," +
                "ExertionLevel TEXT NOT NULL," +
                "LengthOfEvent INTEGER NOT NULL," +
                "Description TEXT);";

        String CREATE_USER_TABLE = "CREATE TABLE UserInfo (" +
                "UserID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Height INTEGER NOT NULL," +
                "Weight INTEGER NOT NULL," +
                "Gender TEXT NOT NULL," +
                "Age INTEGER NOT NULL);";

        db.execSQL(CREATE_FOODS_TABLE);
        db.execSQL(CREATE_FOODS_OWNED_TABLE);
        db.execSQL(CREATE_RECIPES_TABLE);
        db.execSQL(CREATE_RECIPE_INGREDIENTS_TABLE);
        db.execSQL(CREATE_EVENTS_TABLE);
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Foods");
        db.execSQL("DROP TABLE IF EXISTS FoodsOwned");
        db.execSQL("DROP TABLE IF EXISTS Recipes");
        db.execSQL("DROP TABLE IF EXISTS RecipeIngredients");
        db.execSQL("DROP TABLE IF EXISTS Events");
        db.execSQL("DROP TABLE IF EXISTS UserInfo");
        onCreate(db);
    }

    // Adding new Food
    public void addFood(String foodName, int calories, float protein, float fat, float carbs) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("FoodName", foodName);
        values.put("Calories", calories);
        values.put("ProteinContent", protein);
        values.put("FatContent", fat);
        values.put("CarbohydrateContent", carbs);
        db.insert("Foods", null, values);
        db.close();
    }

    // Adding new FoodOwned
    public void addFoodOwned(int foodID, int quantity, String purchaseDate, String expirationDate, String storageLocation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("FoodID", foodID);
        values.put("Quantity", quantity);
        values.put("PurchaseDate", purchaseDate);
        values.put("ExpirationDate", expirationDate);
        values.put("StorageLocation", storageLocation);
        db.insert("FoodsOwned", null, values);
        db.close();
    }

    // Adding new Recipe
    public void addRecipe(String recipeName, String instructions, int prepTime, int cookTime, List<RecipeIngredient> ingredients) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("RecipeName", recipeName);
        values.put("Instructions", instructions);
        values.put("PrepTime", prepTime);
        values.put("CookTime", cookTime);
        long recipeId = db.insert("Recipes", null, values);

        // Add ingredients to RecipeIngredients table
        for (RecipeIngredient ingredient : ingredients) {
            ContentValues ingredientValues = new ContentValues();
            ingredientValues.put("RecipeID", recipeId);
            ingredientValues.put("FoodID", ingredient.getFoodID());
            ingredientValues.put("Quantity", ingredient.getQuantity()); // Assuming quantity is now an int
            db.insert("RecipeIngredients", null, ingredientValues);
        }

        db.close();
    }

    // Adding new Event
    public void addEvent(String eventName, String eventDate, String exertionLevel, int lengthOfEvent, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("EventName", eventName);
        values.put("EventDate", eventDate);
        values.put("ExertionLevel", exertionLevel);
        values.put("LengthOfEvent", lengthOfEvent);
        values.put("Description", description);
        db.insert("Events", null, values);
        db.close();
    }

    // Adding User Info
    public void addUserInfo(int height, int weight, String gender, int age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Height", height);
        values.put("Weight", weight);
        values.put("Gender", gender);
        values.put("Age", age);
        db.insert("UserInfo", null, values);
        db.close();
    }

    // Retrieve all Foods
    public List<Food> getAllFoods() {
        List<Food> foodList = new ArrayList<>();
        String selectQuery = "SELECT * FROM Foods";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Food food = new Food(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getFloat(3),
                        cursor.getFloat(4),
                        cursor.getFloat(5)
                );
                foodList.add(food);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return foodList;
    }

    // Retrieve all FoodsOwned
    public List<FoodOwned> getAllFoodsOwned() {
        List<FoodOwned> foodOwnedList = new ArrayList<>();
        String selectQuery = "SELECT * FROM FoodsOwned";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                FoodOwned foodOwned = new FoodOwned(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                );
                foodOwnedList.add(foodOwned);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return foodOwnedList;
    }

    // Retrieve all Recipes
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipeList = new ArrayList<>();
        String selectQuery = "SELECT * FROM Recipes";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = new Recipe(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getInt(5)
                );
                recipeList.add(recipe);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return recipeList;
    }

    // Retrieve all RecipeIngredients
    public List<RecipeIngredient> getAllRecipeIngredients(int recipeId) {
        List<RecipeIngredient> ingredientList = new ArrayList<>();
        String selectQuery = "SELECT ri.Quantity, f.FoodID " +
                "FROM RecipeIngredients ri JOIN Foods f ON ri.FoodID = f.FoodID " +
                "WHERE ri.RecipeID = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(recipeId)});

        if (cursor.moveToFirst()) {
            do {
                RecipeIngredient ingredient = new RecipeIngredient(
                        cursor.getInt(2),
                        cursor.getInt(3)
                        );
                ingredientList.add(ingredient);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ingredientList;
    }

    // Retrieve all Events
    public List<Event> getAllEvents() {
        List<Event> eventList = new ArrayList<>();
        String selectQuery = "SELECT * FROM Events";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Event event = new Event(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getString(5)
                );
                eventList.add(event);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return eventList;
    }
    public Cursor getUserInfoCursor() {
        String selectQuery = "SELECT * FROM UserInfo LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null); // Return Cursor
    }

    // Retrieve User Info
    public UserInfo getUserInfo() {
        Cursor cursor = getUserInfoCursor(); // Get Cursor
        UserInfo userInfo = null;

        if (cursor.moveToFirst()) {
            userInfo = new UserInfo(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getInt(4)
            );
        }

        cursor.close(); // Close Cursor
        return userInfo; // Return UserInfo object
    }
    public Food getFoodById(int foodID) {
        Food food = null;
        String selectQuery = "SELECT * FROM Foods WHERE FoodID = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(foodID)});

        if (cursor.moveToFirst()) {
            food = new Food(
                    cursor.getInt(0), // FoodID
                    cursor.getString(1), // FoodName
                    cursor.getInt(2), // Calories
                    cursor.getFloat(3), // ProteinContent
                    cursor.getFloat(4), // FatContent
                    cursor.getFloat(5)  // CarbohydrateContent
            );

            // Ensure values are above 0
            if (food.getCalories() <= 0 || food.getProteinContent() < 0 || food.getFatContent() < 0 || food.getCarbohydrateContent() < 0) {
                food = null; // Set to null if any values are invalid
            }
        }
        cursor.close();
        db.close();
        return food;
    }
    // Get events occurring within the next week and return a Cursor
    public Cursor getEventsWithinAWeek() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Get the current date
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // SQL query to get events within the next week
        String selectQuery = "SELECT * FROM Events WHERE EventDate BETWEEN ? AND date(?, '+7 days')";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{currentDate, currentDate});

        return cursor; // Return the Cursor
    }


}
