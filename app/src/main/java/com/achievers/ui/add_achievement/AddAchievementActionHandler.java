package com.achievers.ui.add_achievement;

import android.content.Context;

public class AddAchievementActionHandler implements AddAchievementContract.ActionHandler {

    private final Context mContext;
    private final AddAchievementContract.Presenter mPresenter;

    AddAchievementActionHandler(Context context, AddAchievementContract.Presenter presenter) {
        mContext = context;
        mPresenter = presenter;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void pictureLoaded() {
        mPresenter.pictureLoaded();
    }
}