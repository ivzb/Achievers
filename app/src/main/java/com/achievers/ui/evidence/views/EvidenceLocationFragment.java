package com.achievers.ui.evidence.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.R;
import com.achievers.databinding.EvidenceLocationFragBinding;
import com.achievers.ui.evidence.EvidenceFragment;

public class EvidenceLocationFragment extends EvidenceFragment<EvidenceLocationFragBinding> {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.evidence_location_frag, container, false);

        mDataBinding = EvidenceLocationFragBinding.bind(view);
        mDataBinding.setViewModel(mViewModel);

        return view;
    }
}