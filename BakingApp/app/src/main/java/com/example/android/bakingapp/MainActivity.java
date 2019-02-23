package com.example.android.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.main_recycler_view);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager

        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        RecyclerView.LayoutManager layoutManager;
        if (tabletSize) {
            layoutManager = new GridLayoutManager(this,3);

        } else {
            layoutManager = new LinearLayoutManager(this);

        }
        recyclerView.setLayoutManager(layoutManager);

        ApiHandler apiHandler = new ApiHandler();
        apiHandler.requestData(this);
    }

    public void onBakingDataReceived(String data) {
        ArrayList<Recipe> recipes = parseJSON(data);
        RecyclerView.Adapter mAdapter = new MainAdapter(recipes, 0);
        recyclerView.setAdapter(mAdapter);
    }

    public static ArrayList<Recipe> parseJSON(String data) {

        try {
            JSONArray jsonArray = new JSONArray(data);
            ArrayList<Recipe> recipes = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject base = jsonArray.getJSONObject(i);
                int recipeId = base.getInt("id"); // 1
                String name = base.getString("name"); // nutella pie
                JSONArray ingredients = base.getJSONArray("ingredients");
                JSONArray steps = base.getJSONArray("steps");
                int servings = base.getInt("servings");
                String image = base.getString("image");

                StringBuilder ingredientString = new StringBuilder();
                for (int j = 0; j < ingredients.length(); j++) {

                    JSONObject currentIngredient = ingredients.getJSONObject(j);

                    String ingredient = currentIngredient.getString("ingredient");
                    String quantity = Integer.toString(currentIngredient.getInt("quantity"));
                    String measure = currentIngredient.getString("measure");

                    ingredientString.append(ingredient).append("\n").append(quantity).append("\n").append(measure).append("\n").append("\n\n");

                }

                ArrayList<Step> stepsList = new ArrayList<>();
                for (int k = 0; k < steps.length(); k++) {
                    JSONObject step = steps.getJSONObject(k);

                    int stepId = step.getInt("id");
                    String shortDescription = step.getString("shortDescription");
                    String description = step.getString("description");
                    String videoUrl = step.getString("videoURL");
                    String thumbnailUrl = step.getString("thumbnailURL");

                    stepsList.add(new Step(stepId, shortDescription, description, videoUrl, thumbnailUrl));

                }

                Recipe recipe = new Recipe(recipeId, name, ingredientString.toString(), stepsList, servings, image);

                recipes.add(recipe);
            }

            return recipes;


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
