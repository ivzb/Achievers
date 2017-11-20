package com.achievers.ui.achievement.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.achievers.BR;
import com.achievers.data.entities.Evidence;
import com.achievers.databinding.EvidencesRecyclerItemBinding;
import com.achievers.ui._base._contracts.action_handlers.BaseAdapterActionHandler;
import com.achievers.ui._base.adapters.MultimediaAdapter;
import com.achievers.utils.ui.multimedia.MultimediaView;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;
import com.achievers.utils.ui.multimedia.players.PhotoMultimediaPlayer;
import com.achievers.utils.ui.multimedia.players.VideoMultimediaPlayer;
import com.achievers.utils.ui.multimedia.players.VoiceMultimediaPlayer;

import static com.achievers.utils.ui.multimedia.MultimediaType.Video;
import static com.achievers.utils.ui.multimedia.MultimediaType.Voice;

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

        if (evidence.getMultimediaType() == Video) {
            player = new VideoMultimediaPlayer(
                    mvEvidence,
                    mContext,
                    mExoPlayer,
                    evidence.getUri());
        } else if (evidence.getMultimediaType() == Voice) {
            player = new VoiceMultimediaPlayer(
                    mvEvidence,
                    mContext,
                    mExoPlayer,
                    evidence.getUri());
        } else {
            player = new PhotoMultimediaPlayer(mvEvidence);
        }

        // todo: catch if builder throws null pointer and show message
        mvEvidence.builder(evidence.getMultimediaType())
                .withUrl(evidence.getPreviewUrl())
                .withActionHandler(this)
                .withPlayer(player)
                .build();
    }
}
