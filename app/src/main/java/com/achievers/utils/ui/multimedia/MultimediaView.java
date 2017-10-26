package com.achievers.utils.ui.multimedia;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.achievers.databinding.MultimediaViewBinding;
import com.achievers.ui._base.contracts.action_handlers.BaseMultimediaActionHandler;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaBuilder;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaView;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaViewActionHandler;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaViewModel;

import static com.achievers.utils.Preconditions.checkNotNull;

public class MultimediaView
        extends ConstraintLayout
        implements BaseMultimediaView, BaseMultimediaViewActionHandler {

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
    public void changeState(MultimediaControllerState state) {
        mViewModel.setControllerState(state);
    }

    @Override
    public BaseMultimediaBuilder builder(MultimediaType multimediaType) {
        return new Builder(multimediaType).clean();
    }

    @Override
    public void release() {
        BaseMultimediaPlayer player = mViewModel.getPlayer();
        startPlayer(player, false);
    }

    @Override
    public View getPlayerView() {
        return mBinding.player;
    }

    @Override
    public void onClick() {
        mViewModel.getMultimediaActionHandler().onMultimediaAction(this);
        BaseMultimediaPlayer player = mViewModel.getPlayer();
        startPlayer(player, !isPlaying());
    }

    private boolean isPlaying() {
        return mViewModel.getControllerState() == MultimediaControllerState.Play;
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

    private void startPlayer(BaseMultimediaPlayer player, boolean start) {
        if (player != null) {
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

        private String mPreviewUrl;

        private BaseMultimediaActionHandler mMultimediaActionHandler;
        private BaseMultimediaPlayer mPlayer;

        Builder(MultimediaType type) {
            mType = checkNotNull(type);
            mControllerState = MultimediaControllerState.None;
        }

        @Override
		public BaseMultimediaBuilder withPreviewUrl(String previewUrl) {
            mPreviewUrl = previewUrl;
            return this;
        }

        @Override
		public BaseMultimediaBuilder withControllerState(MultimediaControllerState state) {
            mControllerState = state;
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

            mViewModel.setControllerState(mControllerState);

            mViewModel.setMultimediaActionHandler(mMultimediaActionHandler);
            mViewModel.setPlayer(mPlayer);
        }

        private BaseMultimediaBuilder clean() {
            if (!isNew()) {
                release();
                build();
            }

            return this;
        }

        private boolean isNew() {
            return mViewModel.getType() == null;
        }
    }
}