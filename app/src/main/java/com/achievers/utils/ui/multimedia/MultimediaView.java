package com.achievers.utils.ui.multimedia;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.achievers.R;
import com.achievers.databinding.MultimediaViewBinding;
import com.achievers.ui._base.contracts.BaseMultimediaPlayer;
import com.achievers.ui._base.contracts.action_handlers.BaseActionHandler;
import com.achievers.ui._base.contracts.action_handlers.BaseMultimediaActionHandler;
import com.achievers.ui._base.contracts.views.BaseMultimediaView;

import static com.achievers.utils.Preconditions.checkNotNull;

public class MultimediaView
        extends ConstraintLayout
        implements BaseMultimediaView, BaseActionHandler {

    private MultimediaViewBinding mBinding;

    private MultimediaType mType;
    private BaseMultimediaPlayer mPlayer;
    private boolean mIsPlaying;

    private String mPreviewUrl;

    private boolean mShowControls;
    private int mPlayResource;
    private int mPauseResource;

    private BaseMultimediaActionHandler mActionHandler;

    public MultimediaView(Context context) {
        super(context);
        init(context);
    }

    public MultimediaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MultimediaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public boolean isPlaying() {
        return mIsPlaying;
    }

    @Override
    public void release() {
        mIsPlaying = false;
        executePlayingBinding(mShowControls);

        mPlayer.stop();
    }

    @Override
    public View getPlayerView() {
        return mBinding.playerView;
    }

    @Override
    public void onClick() {
        mIsPlaying = !mIsPlaying;

        executePlayingBinding(false);
        mActionHandler.onMultimediaAction(this);

        if (mIsPlaying) {
            mPlayer.play();
            return;
        }

        mPlayer.stop();
    }

    private void executePlayingBinding(boolean showControls) {
        mBinding.setShowControls(showControls);
        mBinding.setIsPlaying(mIsPlaying);
        mBinding.executePendingBindings();
    }

    private void init(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        mBinding = MultimediaViewBinding.inflate(layoutInflater, this, true);

        mIsPlaying = false;
        mPlayResource = R.drawable.ic_play;
        mPauseResource = R.drawable.ic_pause;
    }

    private void setType(MultimediaType type) {
        mType = type;
    }

    private void setPreviewUrl(String url) {
        mPreviewUrl = url;
    }

    private void setShowControls(boolean showControls) {
        mShowControls = showControls;
    }

    private void setPlayResource(int resource) {
        mPlayResource = resource;
    }

    private void setPauseResource(int resource) {
        mPauseResource = resource;
    }

    private void setActionHandler(BaseMultimediaActionHandler actionHandler) {
        mActionHandler = actionHandler;
    }

    private void setPlayer(BaseMultimediaPlayer player) {
        mPlayer = player;
    }

    private void build() {
        mBinding.setType(mType);
        mBinding.setPreviewUrl(mPreviewUrl);
        mBinding.setActionHandler(this);

        mBinding.setIsPlaying(mIsPlaying);
        mBinding.setShowControls(mShowControls);
        mBinding.setPlayResource(mPlayResource);
        mBinding.setPauseResource(mPauseResource);

        mBinding.setResources(getResources());
        mBinding.executePendingBindings();
    }

    public static class Builder {

        private final MultimediaView mMultimediaView;

        public Builder(MultimediaView multimediaView, MultimediaType type) {
            mMultimediaView = checkNotNull(multimediaView);
            multimediaView.setType(checkNotNull(type));
        }

        public Builder withPreviewUrl(String previewUrl) {
            mMultimediaView.setPreviewUrl(previewUrl);
            return this;
        }

        public Builder withControls(boolean showControls) {
            mMultimediaView.setShowControls(showControls);
            return this;
        }

        public Builder withPlayResource(int resource) {
            mMultimediaView.setPlayResource(resource);
            return this;
        }

        public Builder withPauseResource(int resource) {
            mMultimediaView.setPauseResource(resource);
            return this;
        }

        public Builder withActionHandler(BaseMultimediaActionHandler actionHandler) {
            mMultimediaView.setActionHandler(actionHandler);
            return this;
        }

        public Builder withPlayer(BaseMultimediaPlayer player) {
            mMultimediaView.setPlayer(player);
            return this;
        }

        public void build() {
            mMultimediaView.build();
        }
    }
}