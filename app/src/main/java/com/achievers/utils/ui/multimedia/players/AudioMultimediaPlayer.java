package com.achievers.utils.ui.multimedia.players;

import android.content.Context;
import android.net.Uri;

import com.achievers.BuildConfig;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaViewActionHandler;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import static com.achievers.utils.Preconditions.checkNotNull;
import static com.achievers.utils.ui.multimedia.MultimediaControllerState.Play;

public class AudioMultimediaPlayer extends SimpleMultimediaPlayer {

    private Context mContext;
    private SimpleExoPlayer mExoPlayer;
    private String mUrl;

    public AudioMultimediaPlayer(
            BaseMultimediaViewActionHandler actionHandler,
            Context context,
            SimpleExoPlayer exoPlayer,
            String url) {

        super(actionHandler);
        mActionHandler.changeState(Play);

        mContext = checkNotNull(context);
        mExoPlayer = checkNotNull(exoPlayer);
        mUrl = checkNotNull(url);
    }

    @Override
    public void start() {
        super.start();

        Uri uri = Uri.parse(mUrl);

        String userAgent = Util.getUserAgent(mContext, BuildConfig.APPLICATION_ID);
        MediaSource mediaSource = new ExtractorMediaSource(
                uri,
                new DefaultDataSourceFactory(mContext, userAgent),
                new DefaultExtractorsFactory(),
                null,
                null);

        mExoPlayer.prepare(mediaSource);
        mExoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void stop() {
        super.stop();
        mExoPlayer.setPlayWhenReady(false);
    }
}