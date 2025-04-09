package com.example.myapplication;

public class FoodOwned {
    private int foodOwnedID;
    private int foodID;
    private int quantity;
    private String purchaseDate;
    private String expirationDate;
    private String storageLocation;

    // Constructor
    public FoodOwned(int foodOwnedID, int foodID, int quantity, String purchaseDate, String expirationDate, String storageLocation) {
        this.foodOwnedID = foodOwnedID;
        this.foodID = foodID;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
        this.expirationDate = expirationDate;
        this.storageLocation = storageLocation;
    }

    // Getters and setters
    public int getFoodOwnedID() { return foodOwnedID; }
    public void setFoodOwnedID(int foodOwnedID) { this.foodOwnedID = foodOwnedID; }

    public int getFoodID() { return foodID; }
    public void setFoodID(int foodID) { this.foodID = foodID; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(String purchaseDate) { this.purchaseDate = purchaseDate; }

    public String getExpirationDate() { return expirationDate; }
    public void setExpirationDate(String expirationDate) { this.expirationDate = expirationDate; }

    public String getStorageLocation() { return storageLocation; }
    public void setStorageLocation(String storageLocation) { this.storageLocation = storageLocation; }
}
