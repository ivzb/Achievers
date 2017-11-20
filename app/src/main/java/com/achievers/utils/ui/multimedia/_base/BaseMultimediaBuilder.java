package com.achievers.utils.ui.multimedia._base;

import android.net.Uri;

import com.achievers.ui._base._contracts.action_handlers.BaseMultimediaActionHandler;

public interface BaseMultimediaBuilder {

    BaseMultimediaBuilder withUrl(String url);
    BaseMultimediaBuilder withUri(Uri uri);
//    BaseMultimediaBuilder withControllerState(MultimediaControllerState state);
    BaseMultimediaBuilder withActionHandler(BaseMultimediaActionHandler actionHandler);
    BaseMultimediaBuilder withPlayer(BaseMultimediaPlayer player);

    void build();
}