package com.achievers.utils.ui.multimedia.players;

import android.content.Context;
import android.net.Uri;

import com.achievers.BuildConfig;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaViewActionHandler;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import javax.annotation.Nullable;

import static com.achievers.utils.Preconditions.checkNotNull;
import static com.achievers.utils.ui.multimedia.MultimediaControllerState.Stop;

public abstract class ExoMultimediaPlayer
        extends SimpleMultimediaPlayer
        implements ExoPlayer.EventListener {

    protected Context mContext;
    SimpleExoPlayer mExoPlayer;
    @Nullable SimpleExoPlayerView mExoPlayerView;
    private String mUrl;

    ExoMultimediaPlayer(
            BaseMultimediaViewActionHandler actionHandler,
            Context context,
            SimpleExoPlayer exoPlayer,
            String url) {

        super(actionHandler);

        checkNotNull(context);
        checkNotNull(exoPlayer);
        checkNotNull(url);

        mContext = context;
        mExoPlayer = exoPlayer;
        mExoPlayerView = mActionHandler.getExoPlayerView();
        mUrl = url;
    }

    @Override
    public void init() {
        mActionHandler.changeState(Stop);
    }

    @Override
    public void start() {
        Uri videoUri = Uri.parse(mUrl);

        String userAgent = Util.getUserAgent(mContext, BuildConfig.APPLICATION_ID);
        MediaSource mediaSource = new ExtractorMediaSource(
                videoUri,
                new DefaultDataSourceFactory(mContext, userAgent),
                new DefaultExtractorsFactory(),
                null,
                null);

        mExoPlayer.addListener(this);
        mExoPlayer.prepare(mediaSource);
        mExoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void stop() {
        mExoPlayer.removeListener(this);
        mExoPlayer.setPlayWhenReady(false);
    }

    // exoPlayer events
    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }
}