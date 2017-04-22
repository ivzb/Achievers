package com.achievers.Evidence;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.achievers.AchievementDetail.AchievementDetailContract;
import com.achievers.BR;
import com.achievers.data.Evidence;
import com.achievers.databinding.EvidenceItemBinding;

import java.util.List;

public class EvidenceAdapter extends RecyclerView.Adapter<EvidenceAdapter.ViewHolder> {

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
    public EvidenceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(this.mContext);
        EvidenceItemBinding binding = EvidenceItemBinding.inflate(layoutInflater, parent, false);

        return new EvidenceAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(EvidenceAdapter.ViewHolder viewHolder, int position) {
        Evidence evidence = this.mEvidence.get(position);

        viewHolder.getBinding().setVariable(BR.evidence, evidence);
        viewHolder.getBinding().setVariable(BR.actionHandler, this.mEvidenceItemActionHandler);
        viewHolder.getBinding().executePendingBindings();
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return this.mEvidence.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private EvidenceItemBinding binding;

        public ViewHolder(EvidenceItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public EvidenceItemBinding getBinding() {
            return this.binding;
        }
    }
}