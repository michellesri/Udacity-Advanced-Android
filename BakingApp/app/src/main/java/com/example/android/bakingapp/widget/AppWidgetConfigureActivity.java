package com.example.android.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.bakingapp.ApiHandler;
import com.example.android.bakingapp.MainAdapter;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.Recipe;
import com.example.android.bakingapp.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AppWidgetConfigureActivity extends AppCompatActivity {
    private int mAppWidgetId;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_widget_configure);

        ApiHandler apiHandler = new ApiHandler();
        apiHandler.requestData(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        recyclerView = findViewById(R.id.widget_recycler_view);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager

        RecyclerView.LayoutManager layoutManager;
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);

        if (tabletSize) {
            layoutManager = new GridLayoutManager(this,3);

        } else {
            layoutManager = new LinearLayoutManager(this);

        }
        recyclerView.setLayoutManager(layoutManager);

    }

    public void onBakingDataReceived(String data) {
        ArrayList<Recipe> recipes = parseJSON(data);
        RecyclerView.Adapter mAdapter = new MainAdapter(recipes, mAppWidgetId);
        recyclerView.setAdapter(mAdapter);
    }

    public ArrayList<Recipe> parseJSON(String data) {

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
