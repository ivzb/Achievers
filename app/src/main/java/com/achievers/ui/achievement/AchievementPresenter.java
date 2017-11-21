package com.achievers.ui.achievement;

import android.content.Context;
import android.support.annotation.NonNull;

import com.achievers.data.entities.Evidence;
import com.achievers.data.source.evidences.EvidencesDataSource;
import com.achievers.ui._base.presenters.EndlessAdapterPresenter;

class AchievementPresenter
        extends EndlessAdapterPresenter<Evidence, AchievementContract.View, EvidencesDataSource>
        implements AchievementContract.Presenter {

    AchievementPresenter(
           @NonNull Context context,
           @NonNull AchievementContract.View view,
           @NonNull EvidencesDataSource dataSource) {

        super(context, view, dataSource);
    }

    @Override
    public void result(int requestCode, int resultCode) {
        super.result(requestCode, resultCode);

        // todo
    }
}