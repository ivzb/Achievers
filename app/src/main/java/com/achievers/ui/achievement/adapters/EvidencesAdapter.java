package com.achievers.ui.achievement.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.achievers.BR;
import com.achievers.data.entities.Evidence;
import com.achievers.databinding.EvidencesRecyclerItemBinding;
import com.achievers.ui._base.adapters.MultimediaAdapter;
import com.achievers.ui._base.contracts.BaseMultimediaPlayer;
import com.achievers.ui._base.contracts.action_handlers.BaseAdapterActionHandler;
import com.achievers.utils.ui.multimedia.MultimediaView;
import com.achievers.utils.ui.multimedia.players.SimpleMultimediaPlayer;
import com.achievers.utils.ui.multimedia.players.VideoMultimediaPlayer;

import static com.achievers.data.entities.EvidenceType.Photo;
import static com.achievers.data.entities.EvidenceType.Video;

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

        BaseMultimediaPlayer player;

        if (evidence.getEvidenceType() == Video) {
            player = new VideoMultimediaPlayer(
                    mContext,
                    mExoPlayer,
                    mvEvidence.getPlayerView(),
                    evidence.getUrl());
        } else {
            player = new SimpleMultimediaPlayer();
        }

        // todo: catch if builder throws null pointer
        new MultimediaView.Builder(mvEvidence, )
                .withPreviewUrl(evidence.getPreviewUrl())
                .withControls(evidence.getEvidenceType() != Photo)
                .withPlayResource(evidence.getEvidenceType().getPlayResource())
                .withActionHandler(this)
                .withPlayer(player)
                .build();
    }
}