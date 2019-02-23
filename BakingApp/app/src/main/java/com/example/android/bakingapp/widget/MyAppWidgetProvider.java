package com.example.android.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.example.android.bakingapp.MainAdapter;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.Recipe;
import com.example.android.bakingapp.recipedetail.DetailActivity;
import com.google.gson.Gson;

public class MyAppWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];
            String jsonRecipe = sharedPreferences.getString(Integer.toString(appWidgetId), null);

            if (jsonRecipe != null) {
                Gson gson = new Gson();
                Recipe recipe = gson.fromJson(jsonRecipe, Recipe.class);
                System.out.println(recipe.ingredientString);


                // Tell the widget manager

                // Create an Intent to launch ExampleActivity
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(MainAdapter.CLICKED_RECIPE, recipe);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                // Get the layout for the App Widget and attach an on-click listener
                // to the button
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
                views.setTextViewText(R.id.widget_id, recipe.ingredientString);
                views.setOnClickPendingIntent(R.id.widget_id, pendingIntent);

                // Tell the AppWidgetManager to perform an update on the current app widget
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }

        }
    }
}
