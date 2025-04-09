import requests
import pandas as pd
import argparse
import os

def fetch_recipes(ingredients, api_key, number=5):
    url = "https://api.spoonacular.com/recipes/findByIngredients"
    params = {
        "ingredients": ",".join(ingredients),
        "number": number,
        "ranking": 1,
        "ignorePantry": True,
        "apiKey": api_key
    }
    response = requests.get(url, params=params)
    return response.json() if response.status_code == 200 else None

def fetch_nutrition(recipe_id, api_key):
    url = f"https://api.spoonacular.com/recipes/{recipe_id}/nutritionWidget.json"
    params = {"apiKey": api_key}
    response = requests.get(url, params=params)
    return response.json() if response.status_code == 200 else None

def process_csv(csv_file, api_key):
    df = pd.read_csv(csv_file)
    if "Product" not in df.columns:
        print("Error: CSV file must contain a 'Product' column.")
        return
    
    ingredients = df["Product"].dropna().tolist()
    print(f"Fetching recipes for ingredients: {ingredients}")
    recipes = fetch_recipes(ingredients, api_key)
    
    if not recipes:
        print("No recipes found.")
        return
    
    recipes_data = []
    for recipe in recipes:
        nutrition = fetch_nutrition(recipe["id"], api_key)
        recipes_data.append({
            "Recipe Name": recipe["title"],
            "Image URL": recipe["image"],
            "Used Ingredients": len(recipe["usedIngredients"]),
            "Missed Ingredients": len(recipe["missedIngredients"]),
            "Calories": nutrition.get("calories", "N/A") if nutrition else "N/A",
            "Carbs": nutrition.get("carbs", "N/A") if nutrition else "N/A",
            "Protein": nutrition.get("protein", "N/A") if nutrition else "N/A",
            "Fat": nutrition.get("fat", "N/A") if nutrition else "N/A",
        })
    
    output_df = pd.DataFrame(recipes_data)
    output_file = "recipes_nutrition.csv"
    output_df.to_csv(output_file, index=False)
    print(f"Saved recipe and nutrition data to {output_file}")

def main():
    parser = argparse.ArgumentParser(description='Fetch recipes and nutritional info from Spoonacular API')
    parser.add_argument('--csv', required=True, help='Path to the CSV file containing ingredients')
    parser.add_argument('--api_key', required=True, help='Spoonacular API Key')
    args = parser.parse_args()
    
    process_csv(args.csv, args.api_key)

if __name__ == "__main__":
    main()


    # python C:\Users\keval\smart-receipt-scanner\nutritional_grabber.py --csv C:\Users\keval\smart-receipt-scanner\out\csv\receipt_data.csv --api_key 51c893cc234c4f95bccef5b82e7552a4``