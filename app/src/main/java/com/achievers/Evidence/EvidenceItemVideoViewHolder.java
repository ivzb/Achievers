package com.achievers.Evidence;

import android.support.v7.widget.RecyclerView;

import com.achievers.databinding.EvidenceItemVideoBinding;

public class EvidenceItemVideoViewHolder extends RecyclerView.ViewHolder {
    private EvidenceItemVideoBinding binding;

    public EvidenceItemVideoViewHolder(EvidenceItemVideoBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public EvidenceItemVideoBinding getBinding() {
        return this.binding;
    }
}