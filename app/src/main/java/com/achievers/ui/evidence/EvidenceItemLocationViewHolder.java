package com.achievers.ui.evidence;

import android.support.v7.widget.RecyclerView;

import com.achievers.databinding.EvidenceItemLocationBinding;

import im.ene.toro.ToroAdapter;

class EvidenceItemLocationViewHolder extends ToroAdapter.ViewHolder {

    private EvidenceItemLocationBinding binding;

    EvidenceItemLocationViewHolder(EvidenceItemLocationBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public EvidenceItemLocationBinding getBinding() {
        return this.binding;
    }

    @Override public void bind(RecyclerView.Adapter adapter, Object item) { }
    @Override public void onAttachedToWindow() { }
    @Override public void onDetachedFromWindow() { }
}