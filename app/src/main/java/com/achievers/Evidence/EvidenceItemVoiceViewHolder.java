package com.achievers.Evidence;

import android.support.v7.widget.RecyclerView;

import com.achievers.databinding.EvidenceItemImageBinding;
import com.achievers.databinding.EvidenceItemVoiceBinding;

import im.ene.toro.ToroAdapter;

//public class EvidenceItemVoiceViewHolder extends RecyclerView.ViewHolder {
//    private EvidenceItemVoiceBinding binding;
//
//    public EvidenceItemVoiceViewHolder(EvidenceItemVoiceBinding binding) {
//        super(binding.getRoot());
//        this.binding = binding;
//    }
//
//    public EvidenceItemVoiceBinding getBinding() {
//        return this.binding;
//    }
//}

public class EvidenceItemVoiceViewHolder extends ToroAdapter.ViewHolder {

    private EvidenceItemVoiceBinding binding;

    public EvidenceItemVoiceViewHolder(EvidenceItemVoiceBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public EvidenceItemVoiceBinding getBinding() {
        return this.binding;
    }

    @Override public void bind(RecyclerView.Adapter adapter, Object item) {

    }

    @Override public void onAttachedToWindow() {

    }

    @Override public void onDetachedFromWindow() {

    }
}