package com.example.android.bakingapp.recipedetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.Recipe;

import java.util.ArrayList;

public class DetailFragment extends Fragment {

    public DetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        ArrayList<String> ingredientsAndSteps = new ArrayList<>();
        RecyclerView recyclerView = rootView.findViewById(R.id.detail_recycler_view);
        recyclerView.setHasFixedSize(true);

        Recipe recipe = (Recipe) getArguments().getSerializable("recipe");

        ingredientsAndSteps.add(recipe.ingredientString);

        for (int i = 0; i < recipe.steps.size(); i++) {
            String stepDescription = recipe.steps.get(i).shortDescription;
            ingredientsAndSteps.add(stepDescription);
        }

        RecyclerView.Adapter mAdapter = new DetailAdapter(ingredientsAndSteps, getActivity(), recipe);
        recyclerView.setAdapter(mAdapter);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return rootView;

    }
}
