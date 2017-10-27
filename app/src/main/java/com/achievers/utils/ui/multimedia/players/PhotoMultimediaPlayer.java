package com.achievers.utils.ui.multimedia.players;

import com.achievers.utils.ui.multimedia.MultimediaType;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaViewActionHandler;

import static com.achievers.utils.ui.multimedia.MultimediaControllerState.None;

public class PhotoMultimediaPlayer extends SimpleMultimediaPlayer {

    public PhotoMultimediaPlayer(BaseMultimediaViewActionHandler actionHandler) {
        super(actionHandler);
    }

    @Override
    public void init() {
        mActionHandler.changeState(None, MultimediaType.Photo.getStopDrawable());
    }

    @Override
    public void start() {
        // never accessed
    }

    @Override
    public void stop() {
        // never accessed
    }
}