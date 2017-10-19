package com.achievers.ui.achievement.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.achievers.BR;
import com.achievers.data.entities.Evidence;
import com.achievers.databinding.EvidencesRecyclerItemBinding;
import com.achievers.ui._base.AbstractAdapter;
import com.achievers.ui._base.contracts.BaseActionHandler;
import com.achievers.ui.achievement.EvidenceMultimediaActionHandler;

import java.util.HashMap;
import java.util.TreeSet;

public class EvidencesAdapter
        extends AbstractAdapter<Evidence>
        implements EvidenceMultimediaActionHandler {

    private Context mContext;
    private HashMap<Long, EvidencesAdapter.ViewHolder> mViewHolders;
    private Long mPlayingId;

    public EvidencesAdapter(Context context, BaseActionHandler<Evidence> actionHandler) {
        super(context, actionHandler);

        mContext = context;
        mViewHolders = new HashMap<>();
        mPlayingId = null;
    }

    @Override
    public EvidencesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        EvidencesRecyclerItemBinding binding = EvidencesRecyclerItemBinding.inflate(layoutInflater, parent, false);

        return new ViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Evidence evidence = mEntities.get(position);

        viewHolder.getBinding().setVariable(BR.evidence, evidence);
        viewHolder.getBinding().setVariable(BR.resources, mContext.getResources());
        viewHolder.getBinding().setVariable(BR.adapterActionHandler, mActionHandler);
        viewHolder.getBinding().setVariable(BR.evidenceMultimediaActionHandler, this);
        viewHolder.getBinding().setVariable(BR.multimediaPlaying, mPlayingId != null && mPlayingId == evidence.getId());
        viewHolder.getBinding().setVariable(BR.multimediaDrawable, evidence.getEvidenceType().getDrawable());

        viewHolder.getBinding().executePendingBindings();

        mViewHolders.put(evidence.getId(), viewHolder);
    }

    @Override
    public void onEvidenceMultimediaClick(Evidence evidence) {
        long currentId = evidence.getId();

        stopPreviousItemMultimedia(currentId);
        toggleCurrentItemMultimedia(currentId);
    }

    private void stopPreviousItemMultimedia(long currentId) {
        if (mPlayingId != null && mPlayingId != currentId) {
            EvidencesAdapter.ViewHolder viewHolder = mViewHolders.get(mPlayingId);
            viewHolder.getBinding().setVariable(BR.multimediaPlaying, false);
            viewHolder.getBinding().executePendingBindings();

            mPlayingId = null;
        }
    }

    private void toggleCurrentItemMultimedia(long currentId) {
        boolean isPlaying = mPlayingId == null;

        if (isPlaying) {
            mPlayingId = currentId;
        } else {
            mPlayingId = null;
        }

        EvidencesAdapter.ViewHolder viewHolder = mViewHolders.get(currentId);
        viewHolder.getBinding().setVariable(BR.multimediaPlaying, isPlaying);
        viewHolder.getBinding().executePendingBindings();
    }
}