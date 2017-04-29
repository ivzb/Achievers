package com.achievers.Evidence;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.AchievementDetail.AchievementDetailContract;
import com.achievers.BR;
import com.achievers.R;
import com.achievers.data.Evidence;
import com.achievers.data.EvidenceType;
import com.achievers.databinding.EvidenceItemBinding;
import com.achievers.databinding.EvidenceItemImageBinding;
import com.achievers.databinding.EvidenceItemLocationBinding;
import com.achievers.databinding.EvidenceItemVideoBinding;
import com.achievers.databinding.EvidenceItemVoiceBinding;

import java.util.List;

public class EvidenceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Evidence> mEvidence;
    private EvidenceItemActionHandler mEvidenceItemActionHandler;
    private AchievementDetailContract.Presenter mUserActionsListener;
    private Context mContext;

    public EvidenceAdapter(List<Evidence> evidence, AchievementDetailContract.Presenter userActionsListener) {
        this.mEvidence = evidence;
        this.mEvidenceItemActionHandler = new EvidenceItemActionHandler(userActionsListener);
        this.mUserActionsListener = userActionsListener;
    }

    public Context getContext() {
        return this.mContext;
    }

    @Override
    public int getItemCount() {
        return this.mEvidence.size();
    }

    @Override
    public int getItemViewType(int position) {
        Evidence evidence = this.mEvidence.get(position);

        return evidence.getEvidenceType().getId();
    }

    /**
     * This method creates different RecyclerView.ViewHolder objects based on the item view type.\
     *
     * @param viewGroup ViewGroup container for the item
     * @param viewType type of view to be inflated
     * @return viewHolder to be inflated
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        this.mContext = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(this.mContext);
        RecyclerView.ViewHolder viewHolder;

        switch (EvidenceType.values()[viewType - 1]) {
            case Image:
                EvidenceItemImageBinding imageBinding = EvidenceItemImageBinding.inflate(layoutInflater, viewGroup, false);
                viewHolder = new EvidenceItemImageViewHolder(imageBinding);
                break;
            case Video:
                EvidenceItemVideoBinding videoBinding = EvidenceItemVideoBinding.inflate(layoutInflater, viewGroup, false);
                viewHolder = new EvidenceItemVideoViewHolder(videoBinding);
                break;
            case Voice:
                EvidenceItemVoiceBinding voiceBinding = EvidenceItemVoiceBinding.inflate(layoutInflater, viewGroup, false);
                viewHolder = new EvidenceItemVoiceViewHolder(voiceBinding);
                break;
            case Location:
                EvidenceItemLocationBinding locationBinding = EvidenceItemLocationBinding.inflate(layoutInflater, viewGroup, false);
                viewHolder = new EvidenceItemLocationViewHolder(locationBinding);
                break;
            default:
                viewHolder = null;
                break;
        }

        return viewHolder;
    }

    /**
     * This method internally calls onBindViewHolder(ViewHolder, int) to update the
     * RecyclerView.ViewHolder contents with the item at the given position
     * and also sets up some private fields to be used by RecyclerView.
     *
     * @param viewHolder The type of RecyclerView.ViewHolder to populate
     * @param position Item position in the viewgroup.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (EvidenceType.getById(viewHolder.getItemViewType())) {
            case Image:
                EvidenceItemImageViewHolder imageViewHolder = (EvidenceItemImageViewHolder) viewHolder;
                ConfigureImageViewHolder(imageViewHolder, position);
                break;
            case Video:
                EvidenceItemVideoViewHolder videoViewHolder = (EvidenceItemVideoViewHolder) viewHolder;
                ConfigureVideoViewHolder(videoViewHolder, position);
                break;
            case Voice:
                EvidenceItemVoiceViewHolder voiceViewHolder = (EvidenceItemVoiceViewHolder) viewHolder;
                ConfigureVoiceViewHolder(voiceViewHolder, position);
                break;
            case Location:
                EvidenceItemLocationViewHolder locationViewHolder = (EvidenceItemLocationViewHolder) viewHolder;
                ConfigureLocationViewHolder(locationViewHolder, position);
                break;
        }
    }

    private void ConfigureImageViewHolder(EvidenceItemImageViewHolder imageViewHolder, int position) {
        Evidence evidence = this.mEvidence.get(position);

        imageViewHolder.getBinding().setVariable(BR.evidence, evidence);
        imageViewHolder.getBinding().setVariable(BR.actionHandler, this.mEvidenceItemActionHandler);
        imageViewHolder.getBinding().executePendingBindings();
    }

    private void ConfigureVideoViewHolder(EvidenceItemVideoViewHolder videoViewHolder, int position) {
        Evidence evidence = this.mEvidence.get(position);

        videoViewHolder.getBinding().setVariable(BR.evidence, evidence);
        videoViewHolder.getBinding().setVariable(BR.actionHandler, this.mEvidenceItemActionHandler);
        videoViewHolder.getBinding().executePendingBindings();
    }

    private void ConfigureVoiceViewHolder(EvidenceItemVoiceViewHolder voiceViewHolder, int position) {
        Evidence evidence = this.mEvidence.get(position);

        voiceViewHolder.getBinding().setVariable(BR.evidence, evidence);
        voiceViewHolder.getBinding().setVariable(BR.actionHandler, this.mEvidenceItemActionHandler);
        voiceViewHolder.getBinding().executePendingBindings();
    }

    private void ConfigureLocationViewHolder(EvidenceItemLocationViewHolder locationViewHolder, int position) {
        Evidence evidence = this.mEvidence.get(position);

        locationViewHolder.getBinding().setVariable(BR.evidence, evidence);
        locationViewHolder.getBinding().setVariable(BR.actionHandler, this.mEvidenceItemActionHandler);
        locationViewHolder.getBinding().executePendingBindings();
    }

}