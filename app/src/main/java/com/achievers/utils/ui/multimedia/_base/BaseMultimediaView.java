package com.achievers.utils.ui.multimedia._base;

import com.achievers.ui._base._contracts.action_handlers.BaseActionHandler;
import com.achievers.utils.ui.multimedia.MultimediaType;

public interface BaseMultimediaView
        extends BaseActionHandler, BaseMultimediaViewActionHandler {

    BaseMultimediaBuilder builder(MultimediaType multimediaType);
}