package com.longg.apiretrofitdemo.Model;

public class FoodOfCategories {
    private int id;
    private String name, description, image;
    private float energyPerServing, fat,protein,carb;

    public FoodOfCategories() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getEnergyPerServing() {
        return energyPerServing;
    }

    public void setEnergyPerServing(float energyPerServing) {
        this.energyPerServing = energyPerServing;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getCarb() {
        return carb;
    }

    public void setCarb(float carb) {
        this.carb = carb;
    }
}
