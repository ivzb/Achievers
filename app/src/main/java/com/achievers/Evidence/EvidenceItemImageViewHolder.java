package com.achievers.Evidence;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.achievers.databinding.EvidenceItemImageBinding;

import im.ene.toro.ToroAdapter;

//public class EvidenceItemImageViewHolder extends RecyclerView.ViewHolder {
//    private EvidenceItemImageBinding binding;
//
//    public EvidenceItemImageViewHolder(EvidenceItemImageBinding binding) {
//        super(binding.getRoot());
//        this.binding = binding;
//    }
//
//    public EvidenceItemImageBinding getBinding() {
//        return this.binding;
//    }
//}

public class EvidenceItemImageViewHolder extends ToroAdapter.ViewHolder {

    private EvidenceItemImageBinding binding;

    public EvidenceItemImageViewHolder(EvidenceItemImageBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public EvidenceItemImageBinding getBinding() {
        return this.binding;
    }

    @Override public void bind(RecyclerView.Adapter adapter, Object item) {

    }

    @Override public void onAttachedToWindow() {

    }

    @Override public void onDetachedFromWindow() {

    }
}