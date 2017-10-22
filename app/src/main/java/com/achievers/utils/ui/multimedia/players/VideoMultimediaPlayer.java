package com.achievers.utils.ui.multimedia.players;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.achievers.BuildConfig;
import com.achievers.ui._base.contracts.BaseMultimediaPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import static com.achievers.utils.Preconditions.checkNotNull;

public class VideoMultimediaPlayer implements BaseMultimediaPlayer {

    private Context mContext;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mExoPlayerView;
    private String mUrl;

    public VideoMultimediaPlayer(
            Context context,
            SimpleExoPlayer exoPlayer,
            View playerView,
            String url) {

        mContext = checkNotNull(context);
        mExoPlayer = checkNotNull(exoPlayer);
        mExoPlayerView = checkNotNull((SimpleExoPlayerView) playerView);
        mUrl = checkNotNull(url);
    }

    @Override
    public void play() {
        Uri videoUri = Uri.parse(mUrl);

        mExoPlayerView.setPlayer(mExoPlayer);

        String userAgent = Util.getUserAgent(mContext, BuildConfig.APPLICATION_ID);
        MediaSource mediaSource = new ExtractorMediaSource(
                videoUri,
                new DefaultDataSourceFactory(mContext, userAgent),
                new DefaultExtractorsFactory(),
                null,
                null);

        mExoPlayer.prepare(mediaSource);
        mExoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void stop() {
        mExoPlayer.setPlayWhenReady(false);
    }

    @Override
    public boolean showControls() {
        return false;
    }


}
