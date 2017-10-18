package com.achievers.ui.evidence.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.R;
import com.achievers.databinding.EvidencePhotoFragBinding;
import com.achievers.ui.AdapterSetters;
import com.achievers.ui.evidence.EvidenceFragment;

public class EvidencePhotoFragment extends EvidenceFragment<EvidencePhotoFragBinding> {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.evidence_photo_frag, container, false);

        mDataBinding = EvidencePhotoFragBinding.bind(view);
        mDataBinding.setViewModel(mViewModel);

        AdapterSetters.loadImage(
                mDataBinding.sdvPreview,
                mViewModel.getEvidence().getUrl(),
                getContext().getResources());

        return view;
    }
}