package com.example.android.bakingapp.recipedetail;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.FragmentUtils;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.Recipe;

import java.util.ArrayList;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyViewHolder> {
    private ArrayList<String> mDataset;
    private FragmentActivity mActivity;
    private Recipe mRecipe;

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
    public DetailAdapter(ArrayList<String> myDataset, FragmentActivity activity, Recipe recipe) {
        mDataset = myDataset;
        mActivity = activity;
        mRecipe = recipe;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {

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
        textview.setText(mDataset.get(position));


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    return;
                }
                FragmentUtils.renderFragment(position - 1, mRecipe, mActivity);

            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}