package com.achievers.utils.ui.multimedia;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.achievers.databinding.MultimediaViewBinding;
import com.achievers.ui._base.contracts.action_handlers.BaseMultimediaActionHandler;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaBuilder;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaView;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaViewModel;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import static com.achievers.utils.Preconditions.checkNotNull;

public class MultimediaView
        extends ConstraintLayout
        implements BaseMultimediaView {

    private Context mContext;
    private MultimediaViewBinding mBinding;
    private BaseMultimediaViewModel mViewModel;

//    private boolean mPlaying;

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

    @VisibleForTesting
    public MultimediaView(
            Context context,
            MultimediaViewBinding binding,
            BaseMultimediaViewModel viewModel) {

        super(context);

        mContext = checkNotNull(context);
        init(binding, viewModel);
    }

    @Override
    public BaseMultimediaBuilder builder(MultimediaType multimediaType) {
        return new Builder(multimediaType);
    }

    @Override
    public void onClick() {
        triggerMultimediaActionHandler();
        startPlayer();
    }

    @Override
    public void release() {
        mViewModel.setPlaying(true);
        startPlayer();
    }

    @Override
    public void changeState(MultimediaControllerState state) {
        mViewModel.setControllerState(state);
    }

    @Override
    public SimpleExoPlayerView getExoPlayerView() {
        return (SimpleExoPlayerView) mBinding.exoPlayerView;
    }

    private void init(Context context) {
        mContext = checkNotNull(context);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        MultimediaViewBinding binding = MultimediaViewBinding.inflate(
                layoutInflater,
                this,
                true);

        MultimediaViewModel viewModel = new MultimediaViewModel();

        init(binding, viewModel);
    }

    private void init(MultimediaViewBinding binding, BaseMultimediaViewModel viewModel) {
        checkNotNull(binding);
        checkNotNull(viewModel);

        mBinding = binding;
        mBinding.setViewModel(viewModel);

        mViewModel = viewModel;
        mViewModel.setResources(mContext.getResources());
    }

    private void triggerMultimediaActionHandler() {
        BaseMultimediaActionHandler multimediaActionHandler = mViewModel.getMultimediaActionHandler();

        if (multimediaActionHandler != null) {
            multimediaActionHandler.onMultimediaAction(this);
        }
    }

    private void startPlayer() {
        BaseMultimediaPlayer player = mViewModel.getPlayer();

        if (player != null) {
            boolean start = !mViewModel.isPlaying();

            if (start) {
                player.start();
            } else {
                player.stop();
            }
        }
    }

    public class Builder implements BaseMultimediaBuilder {

        private MultimediaType mType;
        private MultimediaControllerState mControllerState;
        private boolean mPlaying;

        private String mPreviewUrl;

        private BaseMultimediaActionHandler mMultimediaActionHandler;
        private BaseMultimediaPlayer mPlayer;

        Builder(MultimediaType type) {
            mType = checkNotNull(type);
            mControllerState = MultimediaControllerState.None;
            mPlaying = false;

            if (mViewModel.getType() != null) release();
        }

        @Override
		public BaseMultimediaBuilder withPreviewUrl(String previewUrl) {
            mPreviewUrl = previewUrl;
            return this;
        }

//        @Override
//		public BaseMultimediaBuilder withControllerState(MultimediaControllerState state) {
//            mControllerState = state;
//            return this;
//        }

//        @Override
//        public BaseMultimediaBuilder withPlaying(boolean playing) {
//            mPlaying = playing;
//            return this;
//        }

        @Override
		public BaseMultimediaBuilder withActionHandler(BaseMultimediaActionHandler actionHandler) {
            mMultimediaActionHandler = actionHandler;
            return this;
        }

        @Override
		public BaseMultimediaBuilder withPlayer(BaseMultimediaPlayer player) {
            mPlayer = player;
            return this;
        }

        @Override
		public void build() {
            mViewModel.setType(mType);
            mViewModel.setControllerState(mControllerState);
            mViewModel.setPlaying(mPlaying);

            mViewModel.setPreviewUrl(mPreviewUrl);

            mViewModel.setActionHandler(MultimediaView.this);
            mViewModel.setMultimediaActionHandler(mMultimediaActionHandler);
            mViewModel.setPlayer(mPlayer);

            if (mPlayer != null) {
                mPlayer.init();
            }
        }
    }
}