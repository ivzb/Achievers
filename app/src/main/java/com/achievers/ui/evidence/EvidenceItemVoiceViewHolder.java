package com.achievers.ui.evidence;

import android.support.v7.widget.RecyclerView;

import com.achievers.databinding.EvidenceItemVoiceBinding;

import im.ene.toro.ToroAdapter;

class EvidenceItemVoiceViewHolder extends ToroAdapter.ViewHolder {

    private EvidenceItemVoiceBinding binding;

    EvidenceItemVoiceViewHolder(EvidenceItemVoiceBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public EvidenceItemVoiceBinding getBinding() {
        return this.binding;
    }

    @Override public void bind(RecyclerView.Adapter adapter, Object item) { }
    @Override public void onAttachedToWindow() { }
    @Override public void onDetachedFromWindow() { }
}