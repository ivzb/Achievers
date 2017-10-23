package com.achievers.ui._base.contracts.multimedia;

import com.achievers.ui._base.contracts.action_handlers.BaseMultimediaActionHandler;

public interface BaseMultimediaBuilder {

    BaseMultimediaBuilder withPreviewUrl(String previewUrl);
    BaseMultimediaBuilder withControls(boolean showControls);
    BaseMultimediaBuilder withPlayResource(int resource);
    BaseMultimediaBuilder withPauseResource(int resource);
    BaseMultimediaBuilder withActionHandler(BaseMultimediaActionHandler actionHandler);
    BaseMultimediaBuilder withPlayer(BaseMultimediaPlayer player);

    void build();
}