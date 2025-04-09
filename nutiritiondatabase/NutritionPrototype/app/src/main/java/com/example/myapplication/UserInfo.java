package com.example.myapplication;

public class UserInfo {
    private int userID;
    private int height;
    private int weight;
    private String gender;
    private int age;

    // Constructor
    public UserInfo(int userID, int height, int weight, String gender, int age) {
        this.userID = userID;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.age = age;
    }

    // Getters
    public int getUserID() {
        return userID;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    // Setters
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
