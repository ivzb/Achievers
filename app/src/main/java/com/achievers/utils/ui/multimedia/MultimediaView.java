package com.achievers.utils.ui.multimedia;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.achievers.R;
import com.achievers.databinding.MultimediaViewBinding;
import com.achievers.ui._base.contracts.action_handlers.BaseActionHandler;
import com.achievers.ui._base.contracts.action_handlers.BaseMultimediaActionHandler;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaBuilder;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaView;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaViewModel;

import static com.achievers.utils.Preconditions.checkNotNull;

public class MultimediaView
        extends ConstraintLayout
        implements BaseMultimediaView, BaseActionHandler {

    private Context mContext;
    private MultimediaViewBinding mBinding;
    private BaseMultimediaViewModel mViewModel;

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
        return new Builder(multimediaType).clean();
    }

    @Override
    public boolean isPlaying() {
        return mViewModel.isPlaying();
    }

    @Override
    public void stop() {
        togglePlayer(false);
    }

    @Override
    public View getPlayerView() {
        return mBinding.player;
    }

    @Override
    public void onClick() {
        mViewModel.setPlaying(!isPlaying());
        mViewModel.getMultimediaActionHandler().onMultimediaAction(this);

        togglePlayer(isPlaying());
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

    private void togglePlayer(boolean play) {
        BaseMultimediaPlayer player = mViewModel.getPlayer();

        boolean hasPlayer = player != null;
        boolean showControls = !play;

        if (hasPlayer) {
            showControls |= player.showControls();

            if (play) player.play();
            else player.stop();
        }

        mViewModel.setPlaying(play);
        mViewModel.setShowControls(showControls);
    }

    public class Builder implements BaseMultimediaBuilder {

        private MultimediaType mType;
        private boolean mIsPlaying;

        private String mPreviewUrl;

        private boolean mShowControls;
        private int mPlayResource;
        private int mPauseResource;

        private BaseMultimediaActionHandler mMultimediaActionHandler;
        private BaseMultimediaPlayer mPlayer;

        Builder(MultimediaType type) {
            mType = checkNotNull(type);
            mPlayResource = R.drawable.ic_play;
            mPauseResource = R.drawable.ic_pause;
        }

        @Override
		public BaseMultimediaBuilder withPreviewUrl(String previewUrl) {
            mPreviewUrl = previewUrl;
            return this;
        }

        @Override
		public BaseMultimediaBuilder withControls(boolean showControls) {
            mShowControls = showControls;
            return this;
        }

        @Override
		public BaseMultimediaBuilder withPlayResource(int resource) {
            mPlayResource = resource;
            return this;
        }

        @Override
		public BaseMultimediaBuilder withPauseResource(int resource) {
            mPauseResource = resource;
            return this;
        }

        @Override
        public BaseMultimediaBuilder withPlaying(boolean isPlaying) {
            mIsPlaying = isPlaying;
            return this;
        }

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
            mViewModel.setPreviewUrl(mPreviewUrl);
            mViewModel.setActionHandler(MultimediaView.this);

            mViewModel.setPlaying(mIsPlaying);
            mViewModel.setShowControls(mShowControls);
            mViewModel.setPlayResource(mPlayResource);
            mViewModel.setPauseResource(mPauseResource);

            mViewModel.setMultimediaActionHandler(mMultimediaActionHandler);
            mViewModel.setPlayer(mPlayer);
        }

        private BaseMultimediaBuilder clean() {
            if (!isNew()) {
                stop();
                build();
            }

            return this;
        }

        private boolean isNew() {
            return mViewModel.getType() == null;
        }
    }
}