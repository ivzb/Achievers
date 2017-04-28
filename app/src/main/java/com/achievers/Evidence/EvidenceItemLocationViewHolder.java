package com.achievers.Evidence;

import android.support.v7.widget.RecyclerView;

import com.achievers.databinding.EvidenceItemLocationBinding;

public class EvidenceItemLocationViewHolder extends RecyclerView.ViewHolder {
    private EvidenceItemLocationBinding binding;

    public EvidenceItemLocationViewHolder(EvidenceItemLocationBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public EvidenceItemLocationBinding getBinding() {
        return this.binding;
    }
}