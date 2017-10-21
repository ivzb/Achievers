package com.achievers.ui._base.contracts.views;

import android.view.View;

public interface BaseMultimediaView {

    boolean isPlaying();
    void release();
    View getPlayerView();
}