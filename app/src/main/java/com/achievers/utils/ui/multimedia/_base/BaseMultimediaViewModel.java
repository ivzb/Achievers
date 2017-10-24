package com.achievers.utils.ui.multimedia._base;

import android.content.res.Resources;
import android.databinding.Bindable;
import android.databinding.Observable;

import com.achievers.ui._base.contracts.action_handlers.BaseActionHandler;
import com.achievers.ui._base.contracts.action_handlers.BaseMultimediaActionHandler;
import com.achievers.utils.ui.multimedia.MultimediaType;

public interface BaseMultimediaViewModel extends Observable {

    @Bindable
    MultimediaType getType();
    void setType(MultimediaType type);

    @Bindable
    String getPreviewUrl();
    void setPreviewUrl(String previewUrl);

    @Bindable
    BaseActionHandler getActionHandler();
    void setActionHandler(BaseActionHandler actionHandler);

    @Bindable
    boolean getShowControls();
    void setShowControls(boolean showControls);

    @Bindable
    int getPlayResource();
    void setPlayResource(int playResource);

    @Bindable
    int getPauseResource();
    void setPauseResource(int pauseResource);

    @Bindable
    boolean isPlaying();
    void setPlaying(boolean isPlaying);

    @Bindable
    boolean isPlayingVideo();

    @Bindable
    Resources getResources();
    void setResources(Resources resources);

    @Bindable
    BaseMultimediaPlayer getPlayer();
    void setPlayer(BaseMultimediaPlayer player);

    @Bindable
    BaseMultimediaActionHandler getMultimediaActionHandler();
    void setMultimediaActionHandler(BaseMultimediaActionHandler multimediaActionHandler);
}