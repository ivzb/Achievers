package com.achievers.ui.evidence;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.R;
import com.achievers.databinding.EvidenceFragBinding;
import com.achievers.ui._base.AbstractFragment;

public abstract class EvidenceFragment
        extends AbstractFragment<EvidenceContract.Presenter, EvidenceContract.ViewModel, EvidenceFragBinding>
        implements EvidenceContract.View<EvidenceFragBinding> {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.evidence_frag, container, false);

        mDataBinding = EvidenceFragBinding.bind(view);
        mDataBinding.setViewModel(mViewModel);

        mPresenter.requestReadExternalStoragePermission();

        return view;
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {

        mPresenter.deliverPermissionsResult(requestCode, grantResults);
    }

    @Override
    public void finish() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }
}