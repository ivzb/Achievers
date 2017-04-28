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

public class EvidenceAdapter extends RecyclerView.Adapter<EvidenceAdapter.ViewHolder> {

    private List<Evidence> mEvidence;
    private EvidenceItemActionHandler mEvidenceItemActionHandler;
    private AchievementDetailContract.Presenter mUserActionsListener;
    private Context mContext;

    private final int IMAGE = 0, IMAGE = 1;

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

        switch (EvidenceType.values()[viewType]) {
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
        Evidence evidence = this.mEvidence.get(position);

        viewHolder.getBinding().setVariable(BR.evidence, evidence);
        viewHolder.getBinding().setVariable(BR.actionHandler, this.mEvidenceItemActionHandler);
        viewHolder.getBinding().executePendingBindings();

        // consider using switch statement if structure of view holders changes
//        switch (EvidenceType.values()[viewHolder.getItemViewType()]) {
//            case Image:
//                EvidenceItemImageViewHolder imageViewHolder = (EvidenceItemImageViewHolder) viewHolder;
//                ConfigureImageViewHolder(imageViewHolder, position);
//                break;
//            case Video:
//                EvidenceItemImageViewHolder videoViewHolder = (EvidenceItemImageViewHolder) viewHolder;
//                ConfigureVideoViewHolder(videoViewHolder, position);
//                break;
//            case Voice:
//                EvidenceItemVoiceViewHolder voiceViewHolder = (EvidenceItemVoiceViewHolder) viewHolder;
//                ConfigureVoiceViewHolder(voiceViewHolder, position);
//                break;
//            case Location:
//                EvidenceItemLocationViewHolder locationViewHolder = (EvidenceItemLocationViewHolder) viewHolder;
//                ConfigureLocationViewHolder(locationViewHolder, position);
//                break;
//        }
    }

//    @Override
//    public EvidenceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        this.mContext = parent.getContext();
//
//        LayoutInflater layoutInflater = LayoutInflater.from(this.mContext);
//        EvidenceItemBinding binding = EvidenceItemBinding.inflate(layoutInflater, parent, false);
//
//        return new EvidenceAdapter.ViewHolder(binding);
//    }
//
//    @Override
//    public void onBindViewHolder(EvidenceAdapter.ViewHolder viewHolder, int position) {
//
//    }

}