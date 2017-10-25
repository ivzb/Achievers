package com.achievers.utils.ui.multimedia._base;

import com.achievers.ui._base.contracts.action_handlers.BaseMultimediaActionHandler;
import com.achievers.utils.ui.multimedia.MultimediaControllerState;

public interface BaseMultimediaBuilder {

    BaseMultimediaBuilder withPreviewUrl(String previewUrl);
    BaseMultimediaBuilder withControllerState(MultimediaControllerState state);

//    BaseMultimediaBuilder withPlayResource(int resource);
//    BaseMultimediaBuilder withPauseResource(int resource);

    BaseMultimediaBuilder withActionHandler(BaseMultimediaActionHandler actionHandler);
    BaseMultimediaBuilder withPlayer(BaseMultimediaPlayer player);

    void build();
}