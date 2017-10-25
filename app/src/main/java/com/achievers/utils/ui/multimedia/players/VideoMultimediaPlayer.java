package com.achievers.utils.ui.multimedia.players;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.achievers.BuildConfig;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;
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

import static com.achievers.utils.Preconditions.checkNotNull;
import static com.achievers.utils.ui.multimedia.MultimediaControllerState.Play;

public class VideoMultimediaPlayer
        extends SimpleMultimediaPlayer
        implements BaseMultimediaPlayer, ExoPlayer.EventListener {

    private Context mContext;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mExoPlayerView;
    private String mUrl;

    public VideoMultimediaPlayer(
            BaseMultimediaViewActionHandler actionHandler,
            Context context,
            SimpleExoPlayer exoPlayer,
            View playerView,
            String url) {

        super(actionHandler);
        mActionHandler.changeState(Play);

        mContext = checkNotNull(context);
        mExoPlayer = checkNotNull(exoPlayer);
        mExoPlayerView = checkNotNull((SimpleExoPlayerView) playerView);
        mUrl = checkNotNull(url);
    }

    @Override
    public void start() {
        Uri videoUri = Uri.parse(mUrl);

        mExoPlayerView.setPlayer(mExoPlayer);

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