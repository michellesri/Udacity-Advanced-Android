package com.example.android.bakingapp.recipedetail;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.bakingapp.FragmentUtils;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.Recipe;
import com.example.android.bakingapp.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class DetailStepFragment extends Fragment {
    Recipe mRecipe;
    int mPosition;
    FragmentActivity mContext;
    Step mStep;
    SimpleExoPlayer mSimpleExoPlayer;
    PlayerView mPlayerView;

    public DetailStepFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_step, container, false);

        mRecipe = (Recipe) getArguments().getSerializable("recipe");
        mPosition = getArguments().getInt("position");
        mContext = getActivity();

        mStep = mRecipe.steps.get(mPosition);
        System.out.println(mStep.description);

        TextView stepInstructionTv = rootView.findViewById(R.id.recipe_step_instruction);
        stepInstructionTv.setText(mStep.description);

        Button previousBtn = rootView.findViewById(R.id.previous_step_btn);
        Button nextBtn = rootView.findViewById(R.id.next_step_btn);

        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            previousBtn.setVisibility(View.GONE);
            nextBtn.setVisibility(View.GONE);
        }

        if (mPosition == 0) {
            previousBtn.setEnabled(false);
        } else {
            previousBtn.setOnClickListener(v -> FragmentUtils.renderFragment(mPosition - 1, mRecipe, mContext));

        }
        int numStepsPosition = mRecipe.steps.size() - 1;
        if (mPosition == numStepsPosition) {
            nextBtn.setEnabled(false);
        } else {
            nextBtn.setOnClickListener(v -> FragmentUtils.renderFragment(mPosition + 1, mRecipe, mContext));
        }

        mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext);
        mPlayerView = rootView.findViewById(R.id.player_view);
        mPlayerView.setPlayer(mSimpleExoPlayer);

        Uri uri = Uri.parse(mStep.videoUrl);


        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(mContext,
                Util.getUserAgent(mContext, "BakingApp"));
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
        // Prepare the player with the source.
        mSimpleExoPlayer.prepare(videoSource);

        return rootView;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSimpleExoPlayer.release();

    }
}
