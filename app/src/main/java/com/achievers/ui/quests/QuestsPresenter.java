package com.achievers.ui.quests;

import android.support.annotation.NonNull;

import com.achievers.ui._base.AbstractPresenter;

import static com.achievers.utils.Preconditions.checkNotNull;

public class QuestsPresenter
        extends AbstractPresenter<QuestsContract.View>
        implements QuestsContract.Presenter {

    QuestsPresenter(
            @NonNull QuestsContract.View view) {

        mView = checkNotNull(view, "view cannot be null");
    }

    @Override
    public void start() {

    }
}