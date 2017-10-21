package com.achievers.ui.achievement.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.achievers.BR;
import com.achievers.data.entities.Evidence;
import com.achievers.databinding.EvidencesRecyclerItemBinding;
import com.achievers.ui._base.adapters.MultimediaAdapter;
import com.achievers.ui._base.contracts.action_handlers.BaseAdapterActionHandler;
import com.achievers.utils.ui.MultimediaView;

import static com.achievers.data.entities.EvidenceType.Photo;

public class EvidencesAdapter extends MultimediaAdapter<Evidence> {

    public EvidencesAdapter(Context context, BaseAdapterActionHandler<Evidence> actionHandler) {
        super(context, actionHandler);
    }

    @Override
    public EvidencesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        EvidencesRecyclerItemBinding binding = EvidencesRecyclerItemBinding.inflate(inflater, parent, false);

        return new ViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(EvidencesAdapter.ViewHolder viewHolder, int position) {
        Evidence evidence = mEntities.get(position);

        viewHolder.getBinding().setVariable(BR.evidence, evidence);
        viewHolder.getBinding().setVariable(BR.actionHandler, mActionHandler);
        viewHolder.getBinding().executePendingBindings();

        MultimediaView mvEvidence  = ((EvidencesRecyclerItemBinding) viewHolder.getBinding()).mvEvidence;

        new MultimediaView.Builder(mvEvidence)
                .withPreviewUrl(evidence.getPreviewUrl())
                .withUrl(evidence.getUrl())
                .withControls(evidence.getEvidenceType() != Photo)
                .withPlayResource(evidence.getEvidenceType().getPlayResource())
                .withActionHandler(this)
                .withExoPlayer(mExoPlayer)
                .build();
    }
}