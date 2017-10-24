package com.achievers.utils.ui.multimedia._base;

import android.view.View;

import com.achievers.utils.ui.multimedia.MultimediaType;

public interface BaseMultimediaView {

    BaseMultimediaBuilder builder(MultimediaType multimediaType);
    boolean isPlaying();
    void stop();
    View getPlayerView();
}