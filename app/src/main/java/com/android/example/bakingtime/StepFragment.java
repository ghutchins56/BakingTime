package com.android.example.bakingtime;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

public class StepFragment extends Fragment {
    private SimpleExoPlayer ThisSimpleExoPlayer;
    private static final String KEY_STEP = "step";
    private Step ThisStep;

    public StepFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) ThisStep = savedInstanceState.getParcelable(KEY_STEP);
        Context context = getContext();
        View view = inflater.inflate(R.layout.fragment_step, container, false);
        boolean isTablet = getResources().getBoolean(R.bool.is_tablet);
        if (isTablet || (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT)) {
            TextView textDescription = view.findViewById(R.id.text_description);
            textDescription.setText(ThisStep.getDescription());
        }
        ImageView imageStep = view.findViewById(R.id.image_step);
        PlayerView viewVideo = view.findViewById(R.id.view_video);
        String url = ThisStep.getVideoURL();
        if (url.equals("")) {
            imageStep.setVisibility(View.VISIBLE);
            viewVideo.setVisibility(View.GONE);
            url = ThisStep.getThumbnailURL();
            if (url.equals("")) {
                imageStep.setImageResource(R.drawable.bake);
            } else {
                Picasso.get().load(url).into(imageStep);
            }
        } else {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);
            ThisSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
            viewVideo.setPlayer(ThisSimpleExoPlayer);
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, Util.
                    getUserAgent(context, "BakingTime"));
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(ThisStep.getVideoURL()));
            ThisSimpleExoPlayer.prepare(videoSource);
            imageStep.setVisibility(View.GONE);
            viewVideo.setVisibility(View.VISIBLE);
            ThisSimpleExoPlayer.setPlayWhenReady(true);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(KEY_STEP, ThisStep);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (ThisSimpleExoPlayer != null) ThisSimpleExoPlayer.release();
    }

    void setStep(Step step) {
        ThisStep = step;
    }
}
