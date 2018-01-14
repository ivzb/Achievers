package com.achievers.ui.rewards;

import android.content.Context;

import com.achievers.data.entities.Reward;
import com.achievers.data.generators.config.GeneratorConfig;
import com.achievers.data.sources.rewards.RewardsDataSource;
import com.achievers.ui._base.EndlessAdapterPresenterTest;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Random;

import io.bloco.faker.Faker;

public class RewardsPresenterTest
        extends EndlessAdapterPresenterTest<Reward, RewardsContract.Presenter, RewardsContract.View, RewardsDataSource> {

    @Mock protected Context mContext;
    @Mock protected RewardsContract.View mView;
    @Mock protected RewardsDataSource mDataSource;

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public RewardsContract.View getView() {
        return mView;
    }

    @Override
    public RewardsDataSource getDataSource() {
        return mDataSource;
    }

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        GeneratorConfig.destroyInstance();
        GeneratorConfig.initialize(new Random(), new Faker());

        mPresenter = new RewardsPresenter(mContext, mView, mDataSource);
    }

    @Override
    public Reward instantiateModel(String id) {
        if (id == null) return new Reward();
        return new Reward(id);
    }

    @Override
    public RewardsContract.Presenter instantiatePresenter(
            Context context,
            RewardsContract.View view,
            RewardsDataSource dataSource) {

        return new RewardsPresenter(context, view, dataSource);
    }
}
