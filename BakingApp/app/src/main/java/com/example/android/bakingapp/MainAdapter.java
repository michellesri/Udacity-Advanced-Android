package com.example.android.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.android.bakingapp.recipedetail.DetailActivity;
import com.example.android.bakingapp.widget.AppWidgetConfigureActivity;
import com.example.android.bakingapp.widget.MyAppWidgetProvider;
import com.google.gson.Gson;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    private ArrayList<Recipe> mDataset;
    private int mAppWidgetId;


    public static final String CLICKED_RECIPE = "Clicked Recipe Position";

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView cardView;
        public MyViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MainAdapter(ArrayList<Recipe> recipes, int appWidgetId) {
        mDataset = recipes;
        mAppWidgetId = appWidgetId;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MainAdapter.MyViewHolder onCreateViewHolder(final ViewGroup parent,
                                                       int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_card_view, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TextView textview = holder.cardView.findViewById(R.id.title_text);
        final Recipe recipe = mDataset.get(position);
        textview.setText(recipe.name);

        holder.cardView.setOnClickListener(v -> {
            Context context = v.getContext();

            if (context instanceof AppWidgetConfigureActivity) {
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                RemoteViews views = new RemoteViews(context.getPackageName(),
                        R.layout.widget);
                appWidgetManager.updateAppWidget(mAppWidgetId, views);

                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);

                Gson gson = new Gson();
                SharedPreferences.Editor prefsEditor = mPrefs.edit();

                String json = gson.toJson(recipe);
                prefsEditor.putString(Integer.toString(mAppWidgetId), json);
                prefsEditor.apply();

                Intent intent = new Intent(context, MyAppWidgetProvider.class);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                int[] ids = AppWidgetManager.getInstance(context)
                        .getAppWidgetIds(new ComponentName(context, MyAppWidgetProvider.class));
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
                context.sendBroadcast(intent);

                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                ((AppWidgetConfigureActivity) context).setResult(RESULT_OK, resultValue);
                ((AppWidgetConfigureActivity) context).finish();


            } else if (context instanceof MainActivity) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra(CLICKED_RECIPE, recipe);
                context.startActivity(intent);
            }



        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}