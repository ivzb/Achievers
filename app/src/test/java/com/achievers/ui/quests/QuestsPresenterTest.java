package com.achievers.ui.quests;

import android.content.Context;

import com.achievers.data.entities.Quest;
import com.achievers.data.source.quests.QuestsDataSource;
import com.achievers.ui._base.EndlessAdapterPresenterTest;
import com.achievers.utils.GeneratorUtils;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Random;

import io.bloco.faker.Faker;

public class QuestsPresenterTest
        extends EndlessAdapterPresenterTest<Quest, QuestsContract.Presenter, QuestsContract.View, QuestsDataSource> {

    @Mock
    protected Context mContext;
    @Mock protected QuestsContract.View mView;
    @Mock protected QuestsDataSource mDataSource;

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public QuestsContract.View getView() {
        return mView;
    }

    @Override
    public QuestsDataSource getDataSource() {
        return mDataSource;
    }

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        GeneratorUtils.initialize(new Random(), new Faker());

        mPresenter = new QuestsPresenter(mContext, mView, mDataSource);
    }

    @Override
    public Quest instantiateModel(Long id) {
        if (id == null) return new Quest();
        return new Quest(id);
    }

    @Override
    public QuestsContract.Presenter instantiatePresenter(
            Context context,
            QuestsContract.View view,
            QuestsDataSource dataSource) {

        return new QuestsPresenter(context, view, dataSource);
    }
}