package com.achievers.utils.ui.multimedia.players;

import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaViewActionHandler;

import static com.achievers.utils.Preconditions.checkNotNull;

public abstract class SimpleMultimediaPlayer implements BaseMultimediaPlayer {

    protected BaseMultimediaViewActionHandler mActionHandler;

    SimpleMultimediaPlayer(BaseMultimediaViewActionHandler actionHandler) {
        checkNotNull(actionHandler);

        mActionHandler = actionHandler;
    }
}