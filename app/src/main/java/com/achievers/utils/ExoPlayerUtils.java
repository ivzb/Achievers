package com.achievers.utils;

import android.content.Context;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;

import static com.achievers.utils.Preconditions.checkNotNull;

public class ExoPlayerUtils {

    private ExoPlayerUtils() {

    }

    public static SimpleExoPlayer initialize(Context context) {
        checkNotNull(context);

        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();

        return ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
    }
}