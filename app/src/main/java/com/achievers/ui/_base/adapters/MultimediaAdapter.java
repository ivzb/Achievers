package com.achievers.ui._base.adapters;

import android.content.Context;

import com.achievers.data.entities._base.BaseModel;
import com.achievers.ui._base._contracts.action_handlers.BaseAdapterActionHandler;
import com.achievers.ui._base._contracts.action_handlers.BaseMultimediaActionHandler;
import com.achievers.ui._base._contracts.adapters.BaseMultimediaAdapter;
import com.achievers.utils.ExoPlayerUtils;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaView;
import com.google.android.exoplayer2.SimpleExoPlayer;

public abstract class MultimediaAdapter<T extends BaseModel>
        extends ActionHandlerAdapter<T>
        implements BaseMultimediaAdapter<T>, BaseMultimediaActionHandler {

    private BaseMultimediaView mActiveMultimedia;
    protected SimpleExoPlayer mExoPlayer;

    public MultimediaAdapter(Context context, BaseAdapterActionHandler<T> actionHandler) {
        super(context, actionHandler);

        mActiveMultimedia = null;
        mExoPlayer = ExoPlayerUtils.initialize(context);
    }

    @Override
    public void releaseMedia() {
        if (mActiveMultimedia != null) {
            mActiveMultimedia.release();
        }
    }

    @Override
    public void onMultimediaAction(BaseMultimediaView view) {
        if (mActiveMultimedia != view) {
            releaseMedia();
        }

        mActiveMultimedia = view;
    }
}