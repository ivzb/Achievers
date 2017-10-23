package com.achievers.utils.ui.multimedia;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.achievers.R;
import com.achievers.databinding.MultimediaViewBinding;
import com.achievers.ui._base.contracts.multimedia.BaseMultimediaBuilder;
import com.achievers.ui._base.contracts.multimedia.BaseMultimediaPlayer;
import com.achievers.ui._base.contracts.action_handlers.BaseActionHandler;
import com.achievers.ui._base.contracts.action_handlers.BaseMultimediaActionHandler;
import com.achievers.ui._base.contracts.multimedia.BaseMultimediaView;

import static com.achievers.utils.Preconditions.checkNotNull;

public class MultimediaView
        extends ConstraintLayout
        implements BaseMultimediaView, BaseActionHandler {

    private Context mContext;
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

        mContext = context;
        init(inflate(context));
    }

    public MultimediaView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        init(inflate(context));
    }

    public MultimediaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        init(inflate(context));
    }

    @VisibleForTesting
    public MultimediaView(Context context, MultimediaViewBinding binding) {
        super(context);

        mContext = context;
        init(binding);
    }

    private MultimediaViewBinding inflate(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return MultimediaViewBinding.inflate(layoutInflater, this, true);
    }

    private void init(MultimediaViewBinding binding) {
        mBinding = binding;

        mIsPlaying = false;
        mPlayResource = R.drawable.ic_play;
        mPauseResource = R.drawable.ic_pause;
    }

    @Override
    public boolean isPlaying() {
        return mIsPlaying;
    }

    @Override
    public void release() {
        mIsPlaying = false;
        executePlayingBinding();
        executeShowControlsBinding(mShowControls);

        if (mPlayer != null) mPlayer.stop();
    }

    @Override
    public View getPlayerView() {
        return mBinding.player;
    }

    @Override
    public void onClick() {
        mIsPlaying = !mIsPlaying;

        executePlayingBinding();

        boolean showControls = mShowControls;
        if (mPlayer != null) showControls |= mPlayer.showControls();

        executeShowControlsBinding(showControls);
        mActionHandler.onMultimediaAction(this);

        if (mPlayer != null) {
            if (mIsPlaying) {
                mPlayer.play();
                return;
            }

            mPlayer.stop();
        }
    }

    @Override
    public Builder builder(MultimediaType multimediaType) {
        reset();
        return new Builder(this, multimediaType);
    }

    private void reset() {
        mType = null;

        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer = null;
        }

        mIsPlaying = false;
        mPreviewUrl = null;
        mShowControls = false;
        mPlayResource = 0;
        mPauseResource = 0;
    }

    private void executePlayingBinding() {
        mBinding.setIsPlaying(mIsPlaying);
        mBinding.executePendingBindings();
    }

    private void executeShowControlsBinding(boolean showControls) {
        mBinding.setShowControls(showControls);
        mBinding.executePendingBindings();
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
        mIsPlaying = false;

        mBinding.setType(mType);
        mBinding.setPreviewUrl(mPreviewUrl);
        mBinding.setActionHandler(this);

        mBinding.setIsPlaying(mIsPlaying);
        mBinding.setShowControls(mShowControls);
        mBinding.setPlayResource(mPlayResource);
        mBinding.setPauseResource(mPauseResource);

        mBinding.setResources(mContext.getResources());
        mBinding.executePendingBindings();
    }

    public class Builder implements BaseMultimediaBuilder {

        private final MultimediaView mMultimediaView;

        Builder(MultimediaView multimediaView, MultimediaType type) {
            mMultimediaView = checkNotNull(multimediaView);
            multimediaView.setType(checkNotNull(type));
        }

        @Override
		public Builder withPreviewUrl(String previewUrl) {
            mMultimediaView.setPreviewUrl(previewUrl);
            return this;
        }

        @Override
		public Builder withControls(boolean showControls) {
            mMultimediaView.setShowControls(showControls);
            return this;
        }

        @Override
		public Builder withPlayResource(int resource) {
            mMultimediaView.setPlayResource(resource);
            return this;
        }

        @Override
		public Builder withPauseResource(int resource) {
            mMultimediaView.setPauseResource(resource);
            return this;
        }

        @Override
		public Builder withActionHandler(BaseMultimediaActionHandler actionHandler) {
            mMultimediaView.setActionHandler(actionHandler);
            return this;
        }

        @Override
		public Builder withPlayer(BaseMultimediaPlayer player) {
            mMultimediaView.setPlayer(player);
            return this;
        }

        @Override
		public void build() {
            mMultimediaView.build();
        }
    }
}