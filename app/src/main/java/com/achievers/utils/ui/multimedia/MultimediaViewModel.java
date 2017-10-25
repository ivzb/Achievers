package com.achievers.utils.ui.multimedia;

import android.content.res.Resources;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.achievers.BR;
import com.achievers.ui._base.contracts.action_handlers.BaseActionHandler;
import com.achievers.ui._base.contracts.action_handlers.BaseMultimediaActionHandler;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaViewModel;

import static com.achievers.utils.ui.multimedia.MultimediaControllerState.Play;
import static com.achievers.utils.ui.multimedia.MultimediaType.Video;

public class MultimediaViewModel
        extends BaseObservable
        implements BaseMultimediaViewModel {

    private MultimediaType mType;
    private String mPreviewUrl;
    private BaseActionHandler mActionHandler;
    private MultimediaControllerState mControllerState;

    private int mPlayResource;
    private int mPauseResource;

    private Resources mResources;

    private BaseMultimediaPlayer mPlayer;
    private BaseMultimediaActionHandler mMultimediaActionHandler;

    @Bindable
    @Override
    public MultimediaType getType() {
        return mType;
    }

    @Override
    public void setType(MultimediaType type) {
        mType = type;
        notifyPropertyChanged(BR.type);
        notifyPropertyChanged(BR.playingVideo);
    }

    @Bindable
    @Override
    public String getPreviewUrl() {
        return mPreviewUrl;
    }

    @Override
    public void setPreviewUrl(String previewUrl) {
        mPreviewUrl = previewUrl;
        notifyPropertyChanged(BR.previewUrl);
    }

    @Bindable
    @Override
    public BaseActionHandler getActionHandler() {
        return mActionHandler;
    }

    @Override
    public void setActionHandler(BaseActionHandler actionHandler) {
        mActionHandler = actionHandler;
        notifyPropertyChanged(BR.actionHandler);
    }

    @Bindable
    @Override
    public int getPlayResource() {
        return mPlayResource;
    }

    @Override
    public void setPlayResource(int playResource) {
        mPlayResource = playResource;
        notifyPropertyChanged(BR.playResource);
    }

    @Bindable
    @Override
    public int getPauseResource() {
        return mPauseResource;
    }

    @Override
    public void setPauseResource(int pauseResource) {
        mPauseResource = pauseResource;
        notifyPropertyChanged(BR.pauseResource);
    }

    @Bindable
    @Override
    public MultimediaControllerState getControllerState() {
        return mControllerState;
    }

    @Override
    public void setControllerState(MultimediaControllerState controller) {
        mControllerState = controller;
        notifyPropertyChanged(BR.controllerState);
        notifyPropertyChanged(BR.playingVideo);
    }

    @Bindable
    @Override
    public boolean isPlayingVideo() {
        return getControllerState() == Play && getType() == Video;
    }

    @Bindable
    @Override
    public Resources getResources() {
        return mResources;
    }

    @Override
    public void setResources(Resources resources) {
        mResources = resources;
        notifyPropertyChanged(BR.resources);
    }

    @Bindable
    @Override
    public BaseMultimediaPlayer getPlayer() {
        return mPlayer;
    }

    @Override
    public void setPlayer(BaseMultimediaPlayer player) {
        mPlayer = player;
        notifyPropertyChanged(BR.player);
    }

    @Bindable
    @Override
    public BaseMultimediaActionHandler getMultimediaActionHandler() {
        return mMultimediaActionHandler;
    }

    @Override
    public void setMultimediaActionHandler(BaseMultimediaActionHandler multimediaActionHandler) {
        mMultimediaActionHandler = multimediaActionHandler;
        notifyPropertyChanged(BR.multimediaActionHandler);
    }
}