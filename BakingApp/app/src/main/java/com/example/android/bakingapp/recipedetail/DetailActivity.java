package com.example.android.bakingapp.recipedetail;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapp.MainAdapter;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.Recipe;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {

            Intent intent = getIntent();
            Recipe recipe = (Recipe) intent.getSerializableExtra(MainAdapter.CLICKED_RECIPE);

            Bundle bundle = new Bundle();
            bundle.putSerializable("recipe", recipe);

            DetailFragment detailFragment = new DetailFragment();
            detailFragment.setArguments(bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.detail_fragment_container, detailFragment)
                    .commit();
        }

    }

}
