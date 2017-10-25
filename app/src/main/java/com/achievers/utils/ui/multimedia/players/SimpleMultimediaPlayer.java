package com.achievers.utils.ui.multimedia.players;

import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaViewActionHandler;

import static com.achievers.utils.ui.multimedia.MultimediaControllerState.None;

public class SimpleMultimediaPlayer implements BaseMultimediaPlayer {

    protected BaseMultimediaViewActionHandler mActionHandler;

    public SimpleMultimediaPlayer(BaseMultimediaViewActionHandler actionHandler) {
        mActionHandler = actionHandler;
        mActionHandler.changeState(None);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}