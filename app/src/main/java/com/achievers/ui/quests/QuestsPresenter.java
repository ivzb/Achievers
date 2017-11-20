package com.achievers.ui.quests;

import android.content.Context;
import android.support.annotation.NonNull;

import com.achievers.data.entities.Quest;
import com.achievers.data.source.quests.QuestsDataSource;
import com.achievers.ui._base.presenters.EndlessAdapterPresenter;

public class QuestsPresenter
        extends EndlessAdapterPresenter<Quest, QuestsContract.View, QuestsDataSource>
        implements QuestsContract.Presenter {

    QuestsPresenter(
            @NonNull Context context,
            @NonNull QuestsContract.View view,
            @NonNull QuestsDataSource dataSource) {

        super(context, view, dataSource);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }
}