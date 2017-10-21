package com.achievers.utils.ui;

import android.content.Context;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.achievers.BuildConfig;
import com.achievers.R;
import com.achievers.databinding.MultimediaViewBinding;
import com.achievers.ui._base.contracts.action_handlers.BaseActionHandler;
import com.achievers.ui._base.contracts.action_handlers.BaseMultimediaActionHandler;
import com.achievers.ui._base.contracts.views.BaseMultimediaView;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import static com.achievers.utils.Preconditions.checkNotNull;

public class MultimediaView
        extends ConstraintLayout
        implements BaseMultimediaView, BaseActionHandler {

    private MultimediaViewBinding mBinding;

    private boolean mIsPlaying;

    private String mPreviewUrl;
    private String mUrl;

    private boolean mShowControls;
    private int mPlayResource;
    private int mPauseResource;

    private BaseMultimediaActionHandler mActionHandler;

    @Deprecated // extract in VideoMultimediaView
    private SimpleExoPlayer mExoPlayer;

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

        stopVideo();
    }

    @Override
    public void onClick() {
        mIsPlaying = !mIsPlaying;

        executePlayingBinding(false);
        mActionHandler.onMultimediaAction(this);

        if (mIsPlaying) {
            playVideo(mBinding.playerView, getContext(), mUrl);
            return;
        }

        stopVideo();
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

    private void playVideo(
            SimpleExoPlayerView playerView,
            Context context,
            String url) {

        checkNotNull(mExoPlayer);

        Uri videoUri = Uri.parse(url);

        playerView.setPlayer(mExoPlayer);

        String userAgent = Util.getUserAgent(context, BuildConfig.APPLICATION_ID);
        MediaSource mediaSource = new ExtractorMediaSource(
                videoUri,
                new DefaultDataSourceFactory(context, userAgent),
                new DefaultExtractorsFactory(),
                null,
                null);

        mExoPlayer.prepare(mediaSource);
        mExoPlayer.setPlayWhenReady(true);
    }

    private void stopVideo() {
        mExoPlayer.setPlayWhenReady(false);
    }

    private void setPreviewUrl(String url) {
        mPreviewUrl = url;
    }

    private void setUrl(String url) {
        mUrl = url;
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

    @Deprecated // extract in VideoMultimediaView
    private void setExoPlayer(SimpleExoPlayer exoPlayer) {
        mExoPlayer = exoPlayer;
    }

    private void build() {
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

        public Builder(MultimediaView multimediaView) {
            mMultimediaView = checkNotNull(multimediaView);
        }

        public Builder withPreviewUrl(String previewUrl) {
            mMultimediaView.setPreviewUrl(previewUrl);
            return this;
        }

        public Builder withUrl(String url) {
            mMultimediaView.setUrl(url);
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

        @Deprecated // extract in VideoMultimediaView
        public Builder withExoPlayer(SimpleExoPlayer exoPlayer) {
            mMultimediaView.setExoPlayer(exoPlayer);
            return this;
        }

        public void build() {
            mMultimediaView.build();
        }
    }
}