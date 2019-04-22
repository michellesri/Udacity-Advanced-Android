package com.example.android.bakingapp.recipedetail;

import android.media.Image;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.FragmentUtils;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.Recipe;
import com.example.android.bakingapp.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

public class DetailStepFragment extends Fragment {

    private final String VIDEO_POSITION = "video position";
    private final String EXO_PLAYER_STRING = "isExoPlayerPlaying";


    private Recipe mRecipe;
    private int mPosition;
    private FragmentActivity mContext;
    private Step mStep;
    private SimpleExoPlayer mSimpleExoPlayer;
    private PlayerView mPlayerView;
    private boolean mIsExoPlayerPlaying;
    private long mExoPlayerPosition;

    public DetailStepFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_step, container, false);

        if (savedInstanceState != null) {
            mExoPlayerPosition = savedInstanceState.getLong(VIDEO_POSITION);
            mIsExoPlayerPlaying = savedInstanceState.getBoolean(EXO_PLAYER_STRING);

        }

        mRecipe = (Recipe) getArguments().getSerializable("recipe");
        mPosition = getArguments().getInt("position");
        mContext = getActivity();

        mStep = mRecipe.steps.get(mPosition);

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

        return rootView;

    }

    private void initializeExoPlayer() {
        mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext);
        mPlayerView = getView().findViewById(R.id.player_view);
        ImageView thumbnailIv = getView().findViewById(R.id.thumbnail_iv);

        if (mStep.videoUrl.equals("")) {
            mPlayerView.setVisibility(View.GONE);

            Picasso.Builder builder = new Picasso.Builder(mContext);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    thumbnailIv.setImageResource(R.drawable.baking);
                }
            });

            builder.build()
                    .load(mStep.thumbnailUrl)
                    .into(thumbnailIv);
        } else {
            mPlayerView.setPlayer(mSimpleExoPlayer);
            thumbnailIv.setVisibility(View.GONE);

            Uri uri = Uri.parse(mStep.videoUrl);


            // Produces DataSource instances through which media data is loaded.
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(mContext,
                    Util.getUserAgent(mContext, "BakingApp"));
            // This is the MediaSource representing the media to be played.
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(uri);
            // Prepare the player with the source.
            mSimpleExoPlayer.prepare(videoSource);
            mSimpleExoPlayer.seekTo(mExoPlayerPosition);
            mSimpleExoPlayer.setPlayWhenReady(mIsExoPlayerPlaying);

            mSimpleExoPlayer.addListener(new Player.DefaultEventListener() {
                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    if (playWhenReady && playbackState == Player.STATE_READY) {
                        // media actually playing
                        mIsExoPlayerPlaying = true;
                    } else {
                        mIsExoPlayerPlaying = false;
                    }
                }
            });
        }

    }



    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializeExoPlayer();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23) {
            initializeExoPlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            mSimpleExoPlayer.release();
        }
        mExoPlayerPosition = mSimpleExoPlayer.getCurrentPosition();

    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            mSimpleExoPlayer.release();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(VIDEO_POSITION, mSimpleExoPlayer.getCurrentPosition());
        outState.putBoolean(EXO_PLAYER_STRING, mIsExoPlayerPlaying);

    }
}
