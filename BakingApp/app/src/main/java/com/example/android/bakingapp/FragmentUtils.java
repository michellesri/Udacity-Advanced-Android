package com.example.android.bakingapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.example.android.bakingapp.recipedetail.DetailStepFragment;

public class FragmentUtils {
    public static void renderFragment(int position, Recipe recipe, FragmentActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        DetailStepFragment detailStepFragment = new DetailStepFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("recipe", recipe);
        bundle.putInt("position", position);

        detailStepFragment.setArguments(bundle);

        boolean tabletSize = activity.getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            fragmentManager.beginTransaction()
                    .replace(R.id.detail_step_container, detailStepFragment)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.detail_fragment_container, detailStepFragment)
                    .commit();
        }



    }
}
