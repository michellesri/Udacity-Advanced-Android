package com.example.android.bakingapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {

    public int id;
    public String name;
    public String ingredientString;
    public ArrayList<Step> steps;
    public int servings;
    public String image;

    public Recipe(int id, String name, String ingredientString, ArrayList<Step> stepList, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredientString = ingredientString;
        this.steps = stepList;
        this.servings = servings;
        this.image = image;
    }

}
