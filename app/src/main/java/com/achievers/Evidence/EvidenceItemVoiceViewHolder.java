package com.achievers.Evidence;

import android.support.v7.widget.RecyclerView;

import com.achievers.databinding.EvidenceItemVoiceBinding;

public class EvidenceItemVoiceViewHolder extends RecyclerView.ViewHolder {
    private EvidenceItemVoiceBinding binding;

    public EvidenceItemVoiceViewHolder(EvidenceItemVoiceBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public EvidenceItemVoiceBinding getBinding() {
        return this.binding;
    }
}