package com.achievers.utils.ui.multimedia;

import android.content.res.Resources;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.net.Uri;

import com.achievers.BR;
import com.achievers.ui._base._contracts.action_handlers.BaseActionHandler;
import com.achievers.ui._base._contracts.action_handlers.BaseMultimediaActionHandler;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaViewModel;

import static com.achievers.utils.ui.multimedia.MultimediaControllerState.Loading;
import static com.achievers.utils.ui.multimedia.MultimediaControllerState.None;
import static com.achievers.utils.ui.multimedia.MultimediaType.Video;

public class MultimediaViewModel
        extends BaseObservable
        implements BaseMultimediaViewModel {

    private MultimediaType mType;

    private String mUrl;
    private Uri mUri;

    private boolean mPlaying;
    private BaseActionHandler mActionHandler;

    private MultimediaControllerState mControllerState;
    private int mControllerDrawable;

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
    public String getUrl() {
        return mUrl;
    }

    @Override
    public void setUrl(String url) {
        mUrl = url;
        notifyPropertyChanged(BR.url);
    }

    @Bindable
    @Override
    public Uri getUri() {
        return mUri;
    }

    @Override
    public void setUri(Uri uri) {
        mUri = uri;
        notifyPropertyChanged(BR.uri);
    }

    @Bindable
    @Override
    public boolean isPlaying() {
        return mPlaying;
    }

    @Override
    public void setPlaying(boolean playing) {
        mPlaying = playing;
        notifyPropertyChanged(BR.playing);
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
    public MultimediaControllerState getControllerState() {
        return mControllerState;
    }

    @Override
    public void setControllerState(MultimediaControllerState state) {
        mControllerState = state;
        notifyPropertyChanged(BR.controllerState);
        notifyPropertyChanged(BR.playingVideo);
    }

    @Bindable
    @Override
    public int getControllerDrawable() {
        return mControllerDrawable;
    }

    @Override
    public void setControllerDrawable(int drawable) {
        mControllerDrawable = drawable;
        notifyPropertyChanged(BR.controllerDrawable);
    }

    @Bindable
    @Override
    public boolean isPlayingVideo() {
        MultimediaControllerState state = getControllerState();
        return (state == None || state == Loading) && getType() == Video;
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