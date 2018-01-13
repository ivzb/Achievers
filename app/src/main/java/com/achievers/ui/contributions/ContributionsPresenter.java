package com.achievers.ui.contributions;

import android.content.Context;
import android.support.annotation.NonNull;

import com.achievers.data.entities.Contribution;
import com.achievers.data.sources.contributions.ContributionsDataSource;
import com.achievers.ui._base.presenters.EndlessAdapterPresenter;

public class ContributionsPresenter
        extends EndlessAdapterPresenter<Contribution, ContributionsContract.View, ContributionsDataSource>
        implements ContributionsContract.Presenter {

    public ContributionsPresenter(
            @NonNull Context context,
            @NonNull ContributionsContract.View view,
            @NonNull ContributionsDataSource dataSource) {

        super(context, view, dataSource);
    }

}
