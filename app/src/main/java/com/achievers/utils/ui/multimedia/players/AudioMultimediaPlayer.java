package com.achievers.utils.ui.multimedia.players;

import android.content.Context;

import com.achievers.utils.ui.multimedia._base.BaseMultimediaViewActionHandler;
import com.google.android.exoplayer2.SimpleExoPlayer;

import static com.achievers.utils.ui.multimedia.MultimediaControllerState.Play;
import static com.achievers.utils.ui.multimedia.MultimediaControllerState.Stop;

public class AudioMultimediaPlayer extends ExoMultimediaPlayer {

    public AudioMultimediaPlayer(
            BaseMultimediaViewActionHandler actionHandler,
            Context context,
            SimpleExoPlayer exoPlayer,
            String url) {

        super(actionHandler, context, exoPlayer, url);
    }

    @Override
    public void start() {
        super.start();
        mActionHandler.changeState(Play);
    }

    @Override
    public void stop() {
        super.stop();
        mActionHandler.changeState(Stop);
    }
}