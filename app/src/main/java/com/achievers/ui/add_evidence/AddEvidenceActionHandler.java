package com.achievers.ui.add_evidence;

import android.content.Context;

public class AddEvidenceActionHandler implements AddEvidenceContract.ActionHandler {

    private final Context mContext;
    private final AddEvidenceContract.Presenter mPresenter;

    AddEvidenceActionHandler(Context context, AddEvidenceContract.Presenter presenter) {
        mContext = context;
        mPresenter = presenter;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void pictureLoaded(boolean isSuccessful) {
        mPresenter.multimediaLoaded(isSuccessful);
    }
}