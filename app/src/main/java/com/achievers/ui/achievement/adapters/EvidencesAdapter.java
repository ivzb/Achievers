package com.achievers.ui.achievement.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.LongSparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.achievers.BR;
import com.achievers.data.entities.Evidence;
import com.achievers.data.entities.EvidenceType;
import com.achievers.databinding.EvidencesRecyclerItemBinding;
import com.achievers.ui._base.AbstractAdapter;
import com.achievers.ui._base.contracts.BaseActionHandler;
import com.achievers.ui.achievement.EvidenceMultimediaActionHandler;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;

public class EvidencesAdapter
        extends AbstractAdapter<Evidence>
        implements EvidenceMultimediaActionHandler {

    private Context mContext;
    private LongSparseArray<ViewModel> mViewModels;
    private Long mPlayingId;

    private SimpleExoPlayer mExoPlayer;

    public EvidencesAdapter(Context context, BaseActionHandler<Evidence> actionHandler) {
        super(context, actionHandler);

        mContext = context;
        mViewModels = new LongSparseArray<>();
        mPlayingId = null;
        initializePlayer(mContext);
    }

    @Override
    public EvidencesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        EvidencesRecyclerItemBinding binding = EvidencesRecyclerItemBinding.inflate(inflater, parent, false);

        return new ViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Evidence evidence = mEntities.get(position);

        ViewModel viewModel = new ViewModel(
                evidence,
                mActionHandler,
                this,
                mPlayingId != null && mPlayingId == evidence.getId()
        );

        viewHolder.getBinding().setVariable(BR.viewModel, viewModel);
        viewHolder.getBinding().executePendingBindings();

        mViewModels.put(evidence.getId(), viewModel);
    }

    @Override
    public void onEvidenceMultimediaClick(Evidence evidence) {
        long currentId = evidence.getId();

        stopPreviousItemMultimedia(currentId);
        toggleCurrentItemMultimedia(currentId);
    }

    private void stopPreviousItemMultimedia(long currentId) {
        if (mPlayingId != null && mPlayingId != currentId) {
            ViewModel viewModel = mViewModels.get(mPlayingId);
            viewModel.setPlayingMultimedia(false);

            mPlayingId = null;
        }
    }

    private void toggleCurrentItemMultimedia(long currentId) {
        boolean isPlaying = mPlayingId == null;

        ViewModel viewModel = mViewModels.get(currentId);
        viewModel.setPlayingMultimedia(isPlaying);

        mPlayingId = isPlaying ? currentId : null;
    }

    private void initializePlayer(Context context) {
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
    }

    public class ViewModel extends BaseObservable {

        private Evidence mEvidence;
        private BaseActionHandler<Evidence> mEntityActionHandler;
        private EvidenceMultimediaActionHandler mMultimediaActionHandler;

        private boolean mIsPlayingMultimedia;

        public ViewModel(
                Evidence evidence,
                BaseActionHandler<Evidence> entitiesActionHandler,
                EvidenceMultimediaActionHandler multimediaActionHandler,
                boolean isPlayingMultimedia) {

            mEvidence = evidence;
            mEntityActionHandler = entitiesActionHandler;
            mMultimediaActionHandler = multimediaActionHandler;
            mIsPlayingMultimedia = isPlayingMultimedia;
        }

        @Bindable
        public Evidence getEvidence() {
            return mEvidence;
        }

        @Bindable
        public Context getContext() {
            return mContext;
        }

        @Bindable
        public Resources getResources() {
            return mContext.getResources();
        }

        @Bindable
        public BaseActionHandler<Evidence> getEntitiesActionHandler() {
            return mEntityActionHandler;
        }

        @Bindable
        public EvidenceMultimediaActionHandler getMultimediaActionHandler() {
            return mMultimediaActionHandler;
        }

        @Bindable
        public SimpleExoPlayer getExoPlayer() {
            if (mEvidence.getEvidenceType() == EvidenceType.Video) {
                return mExoPlayer;
            }

            return null;
        }

        @Bindable
        public boolean getShowMultimediaControls() {
            return mEvidence.getEvidenceType() != EvidenceType.Photo && !isPlayingVideo();
        }

        @Bindable
        public boolean isPlayingMultimedia() {
            return mIsPlayingMultimedia;
        }

        void setPlayingMultimedia(boolean isPlaying) {
            mIsPlayingMultimedia = isPlaying;
            notifyPropertyChanged(BR.playingMultimedia);
            notifyPropertyChanged(BR.playingVideo);
            notifyPropertyChanged(BR.showMultimediaControls);
        }

        @Bindable
        public int getMultimediaDrawable() {
            return mEvidence.getEvidenceType().getDrawable();
        }

        @Bindable
        public boolean isPlayingVideo() {
            return mIsPlayingMultimedia && mEvidence.getEvidenceType() == EvidenceType.Video;
        }
    }
}