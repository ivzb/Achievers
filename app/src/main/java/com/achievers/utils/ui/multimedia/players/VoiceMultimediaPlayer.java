package com.achievers.utils.ui.multimedia.players;

import android.content.Context;
import android.net.Uri;

import com.achievers.utils.ui.multimedia.MultimediaType;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaViewActionHandler;
import com.google.android.exoplayer2.SimpleExoPlayer;

import static com.achievers.utils.ui.multimedia.MultimediaControllerState.Play;
import static com.achievers.utils.ui.multimedia.MultimediaControllerState.Stop;

public class VoiceMultimediaPlayer extends ExoMultimediaPlayer {

    public VoiceMultimediaPlayer(
            BaseMultimediaViewActionHandler actionHandler,
            Context context,
            SimpleExoPlayer exoPlayer,
            Uri uri) {

        super(actionHandler, context, exoPlayer, uri);

        mPlayState = Play;
        mStopState = Stop;

        mPlayDrawable = MultimediaType.Voice.getPlayDrawable();
        mStopDrawable = MultimediaType.Voice.getStopDrawable();
    }
}
