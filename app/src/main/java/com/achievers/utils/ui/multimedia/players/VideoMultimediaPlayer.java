package com.achievers.utils.ui.multimedia.players;

import android.content.Context;
import android.net.Uri;

import com.achievers.utils.ui.multimedia.MultimediaType;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaViewActionHandler;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;

import static com.achievers.utils.ui.multimedia.MultimediaControllerState.None;
import static com.achievers.utils.ui.multimedia.MultimediaControllerState.Stop;

public class VideoMultimediaPlayer
        extends ExoMultimediaPlayer
        implements BaseMultimediaPlayer, ExoPlayer.EventListener {

    public VideoMultimediaPlayer(
            BaseMultimediaViewActionHandler actionHandler,
            Context context,
            SimpleExoPlayer exoPlayer,
            Uri uri) {

        super(actionHandler, context, exoPlayer, uri);

        mPlayState = None;
        mStopState = Stop;

        mPlayDrawable = MultimediaType.Video.getPlayDrawable();
        mStopDrawable = MultimediaType.Video.getStopDrawable();
    }

    @Override
    public void start() {
        super.start();

        if (mExoPlayerView != null) {
            mExoPlayerView.setPlayer(mExoPlayer);
        }
    }
}