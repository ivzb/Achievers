package com.achievers.Evidence;

import android.support.v7.widget.RecyclerView;

import com.achievers.databinding.EvidenceItemImageBinding;
import com.achievers.databinding.EvidenceItemLocationBinding;

import im.ene.toro.ToroAdapter;

//public class EvidenceItemLocationViewHolder extends RecyclerView.ViewHolder {
//    private EvidenceItemLocationBinding binding;
//
//    public EvidenceItemLocationViewHolder(EvidenceItemLocationBinding binding) {
//        super(binding.getRoot());
//        this.binding = binding;
//    }
//
//    public EvidenceItemLocationBinding getBinding() {
//        return this.binding;
//    }
//}

public class EvidenceItemLocationViewHolder extends ToroAdapter.ViewHolder {

    private EvidenceItemLocationBinding binding;

    public EvidenceItemLocationViewHolder(EvidenceItemLocationBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public EvidenceItemLocationBinding getBinding() {
        return this.binding;
    }

    @Override public void bind(RecyclerView.Adapter adapter, Object item) {

    }

    @Override public void onAttachedToWindow() {

    }

    @Override public void onDetachedFromWindow() {

    }
}