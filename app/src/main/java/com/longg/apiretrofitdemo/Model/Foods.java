package com.longg.apiretrofitdemo.Model;

import com.google.gson.Gson;

public class Foods {
    private int id;
    private String name, description, image;
    private float energyPerServing, fat,protein,carb;
    Category category;

    public Foods(){
    }

    public Foods(float carb,Category category, String description, float energyPerServing, float fat, int id, String image, String name, float protein) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.energyPerServing = energyPerServing;
        this.fat = fat;
        this.protein = protein;
        this.carb = carb;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
