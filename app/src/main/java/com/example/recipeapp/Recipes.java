package com.example.recipeapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.FindIterable;
import org.bson.Document;


public class Recipes implements Serializable {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String title;
    private String category;
    private int preparingTimeMins;
    private int cookingTimeMins;
    private String ingredients;
    private String procedure;

    public Recipes() {
    }

    public Recipes(String id, String title, String category, int preparingTimeMins, int cookingTimeMins, String ingredients, String procedure) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.preparingTimeMins = preparingTimeMins;
        this.cookingTimeMins = cookingTimeMins;
        this.ingredients = ingredients;
        this.procedure = procedure;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPreparingTimeMins() {
        return Integer.toString(preparingTimeMins);
    }

    public void setPreparingTimeMins(String preparingTimeMins) {
        this.preparingTimeMins = Integer.parseInt(preparingTimeMins);
    }

    public String getCookingTimeMins() {
        return Integer.toString(cookingTimeMins);
    }

    public void setCookingTimeMins(String cookingTimeMins) {
        this.cookingTimeMins = Integer.parseInt(cookingTimeMins);
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }
}


