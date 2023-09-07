package com.longg.apiretrofitdemo.Model;

import com.google.gson.Gson;

import java.util.List;

public class Categories {
    private int id;
    private String name, image;
    private List<FoodOfCategories> foods;

    public Categories() {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<FoodOfCategories> getFoods() {
        return foods;
    }

    public void setFoods(List<FoodOfCategories> foods) {
        this.foods = foods;
    }
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
