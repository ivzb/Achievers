package com.achievers.ui._base.adapters;

import android.content.Context;

import com.achievers.data.entities._base.BaseModel;
import com.achievers.ui._base.AbstractAdapter;
import com.achievers.ui._base.contracts.action_handlers.BaseAdapterActionHandler;
import com.achievers.ui._base.contracts.action_handlers.BaseMultimediaActionHandler;
import com.achievers.ui._base.contracts.adapters.BaseMultimediaAdapter;
import com.achievers.ui._base.contracts.multimedia.BaseMultimediaView;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;

public abstract class MultimediaAdapter<T extends BaseModel>
        extends AbstractAdapter<T>
        implements BaseMultimediaAdapter<T>, BaseMultimediaActionHandler {

    private BaseMultimediaView mActiveMultimedia;
    protected SimpleExoPlayer mExoPlayer;

    public MultimediaAdapter(Context context, BaseAdapterActionHandler<T> actionHandler) {
        super(context, actionHandler);

        mActiveMultimedia = null;
        initializePlayer(context);
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

    private void initializePlayer(Context context) {
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
    }
}
