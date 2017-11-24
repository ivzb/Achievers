package com.achievers.ui.rewards;

import android.content.Context;
import android.support.annotation.NonNull;

import com.achievers.data.entities.Reward;
import com.achievers.data.sources.rewards.RewardsDataSource;
import com.achievers.ui._base.presenters.EndlessAdapterPresenter;

public class RewardsPresenter
        extends EndlessAdapterPresenter<Reward, RewardsContract.View, RewardsDataSource>
        implements RewardsContract.Presenter {

    RewardsPresenter(
            @NonNull Context context,
            @NonNull RewardsContract.View view,
            @NonNull RewardsDataSource dataSource) {

        super(context, view, dataSource);
    }
}