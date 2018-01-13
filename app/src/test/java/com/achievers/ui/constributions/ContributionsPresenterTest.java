package com.achievers.ui.constributions;

import android.content.Context;

import com.achievers.data.entities.Contribution;
import com.achievers.data.generators.config.GeneratorConfig;
import com.achievers.data.sources.contributions.ContributionsDataSource;
import com.achievers.ui._base.EndlessAdapterPresenterTest;
import com.achievers.ui.contributions.ContributionsContract;
import com.achievers.ui.contributions.ContributionsPresenter;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Random;

import io.bloco.faker.Faker;

public class ContributionsPresenterTest
        extends EndlessAdapterPresenterTest<Contribution, ContributionsContract.Presenter, ContributionsContract.View, ContributionsDataSource> {

    @Mock protected Context mContext;
    @Mock protected ContributionsContract.View mView;
    @Mock protected ContributionsDataSource mDataSource;

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public ContributionsContract.View getView() {
        return mView;
    }

    @Override
    public ContributionsDataSource getDataSource() {
        return mDataSource;
    }

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        GeneratorConfig.destroyInstance();
        GeneratorConfig.initialize(new Random(), new Faker());

        mPresenter = new ContributionsPresenter(mContext, mView, mDataSource);
    }

    @Override
    public Contribution instantiateModel(Long id) {
        if (id == null) return new Contribution();
        return new Contribution(id);
    }

    @Override
    public ContributionsContract.Presenter instantiatePresenter(
            Context context,
            ContributionsContract.View view,
            ContributionsDataSource dataSource) {

        return new ContributionsPresenter(context, view, dataSource);
    }
}
