package com.achievers.Evidence;

import android.support.v7.widget.RecyclerView;

import com.achievers.databinding.EvidenceItemImageBinding;

public class EvidenceItemImageViewHolder extends RecyclerView.ViewHolder {
    private EvidenceItemImageBinding binding;

    public EvidenceItemImageViewHolder(EvidenceItemImageBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public EvidenceItemImageBinding getBinding() {
        return this.binding;
    }
}