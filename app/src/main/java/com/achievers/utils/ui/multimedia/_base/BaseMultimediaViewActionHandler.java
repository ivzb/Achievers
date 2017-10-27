package com.achievers.utils.ui.multimedia._base;

import com.achievers.utils.ui.multimedia.MultimediaControllerState;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

public interface BaseMultimediaViewActionHandler {

    SimpleExoPlayerView getExoPlayerView();
    void changeState(MultimediaControllerState state, int drawable);
    void release();
}