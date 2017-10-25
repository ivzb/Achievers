package com.achievers.utils.ui.multimedia._base;

import android.view.View;

import com.achievers.ui._base.contracts.action_handlers.BaseActionHandler;
import com.achievers.utils.ui.multimedia.MultimediaType;

public interface BaseMultimediaView extends BaseActionHandler {

    BaseMultimediaBuilder builder(MultimediaType multimediaType);
    boolean isPlaying();
    void stop();
    View getPlayerView();
}