package com.achievers.utils.ui.multimedia._base;

import com.achievers.ui._base.contracts.action_handlers.BaseMultimediaActionHandler;

public interface BaseMultimediaBuilder {

    BaseMultimediaBuilder withPreviewUrl(String previewUrl);
//    BaseMultimediaBuilder withControllerState(MultimediaControllerState state);
    BaseMultimediaBuilder withActionHandler(BaseMultimediaActionHandler actionHandler);
    BaseMultimediaBuilder withPlayer(BaseMultimediaPlayer player);

    void build();
}