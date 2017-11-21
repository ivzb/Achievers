package com.achievers.ui.quest;

import android.content.Context;
import android.support.annotation.NonNull;

import com.achievers.data.entities.Achievement;
import com.achievers.data.source.achievements.AchievementsDataSource;
import com.achievers.ui._base.presenters.EndlessAdapterPresenter;

class QuestPresenter
        extends EndlessAdapterPresenter<Achievement, QuestContract.View, AchievementsDataSource>
        implements QuestContract.Presenter {

    QuestPresenter(
            @NonNull Context context,
            @NonNull QuestContract.View view,
            @NonNull AchievementsDataSource dataSource) {

        super(context, view, dataSource);
    }

    @Override
    public void load(Long id, final int page) {
        if (!mView.isActive()) return;

        mView.setLoadingIndicator(true);

        mDataSource.loadByQuestId(id, page, mLoadCallback);
    }

    @Override
    public void result(int requestCode, int resultCode) {
        super.result(requestCode, resultCode);

        // todo
    }
}