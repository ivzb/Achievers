package com.achievers.ui.contributions;

import android.content.Context;
import android.support.annotation.NonNull;

import com.achievers.data.entities.AchievementProgress;
import com.achievers.data.sources.achievements_progress.AchievementsProgressDataSource;
import com.achievers.ui._base.presenters.EndlessAdapterPresenter;

public class ContributionsPresenter
        extends EndlessAdapterPresenter<AchievementProgress, ContributionsContract.View, AchievementsProgressDataSource>
        implements ContributionsContract.Presenter {

    public ContributionsPresenter(
            @NonNull Context context,
            @NonNull ContributionsContract.View view,
            @NonNull AchievementsProgressDataSource dataSource) {

        super(context, view, dataSource);
    }

}
