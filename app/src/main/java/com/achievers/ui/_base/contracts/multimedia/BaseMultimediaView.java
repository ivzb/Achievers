package com.achievers.ui._base.contracts.multimedia;

import android.view.View;

import com.achievers.utils.ui.multimedia.MultimediaType;
import com.achievers.utils.ui.multimedia.MultimediaView;

public interface BaseMultimediaView {

    boolean isPlaying();
    void release();
    View getPlayerView();
    MultimediaView.Builder builder(MultimediaType multimediaType);
}