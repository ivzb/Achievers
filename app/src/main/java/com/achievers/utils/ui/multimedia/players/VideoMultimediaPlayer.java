package com.achievers.utils.ui.multimedia.players;

import android.content.Context;

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
            String url) {

        super(actionHandler, context, exoPlayer, url);
    }

    @Override
    public void start() {
        super.start();

        if (mExoPlayerView != null) {
            mExoPlayerView.setPlayer(mExoPlayer);
        }

        mActionHandler.changeState(None);
    }

    @Override
    public void stop() {
        super.stop();
        mActionHandler.changeState(Stop);
    }
}