package com.achievers.ui.achievement;

import android.content.Context;

import com.achievers.data.entities.Evidence;
import com.achievers.data.generators.config.GeneratorConfig;
import com.achievers.data.sources.evidences.EvidencesDataSource;
import com.achievers.ui._base.EndlessAdapterPresenterTest;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Random;

import io.bloco.faker.Faker;

public class AchievementPresenterTest
        extends EndlessAdapterPresenterTest<Evidence, AchievementContract.Presenter, AchievementContract.View, EvidencesDataSource> {

    @Mock private Context mContext;
    @Mock private AchievementContract.View mView;
    @Mock private EvidencesDataSource mDataSource;

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public AchievementContract.View getView() {
        return mView;
    }

    @Override
    public EvidencesDataSource getDataSource() {
        return mDataSource;
    }

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        GeneratorConfig.destroyInstance();
        GeneratorConfig.initialize(new Random(), new Faker());

        mPresenter = new AchievementPresenter(mContext, mView, mDataSource);
        mId = 5L;
    }

    @Override
    public Evidence instantiateModel(Long id) {
        if (id == null) return new Evidence();
        return new Evidence(id);
    }

    @Override
    public AchievementContract.Presenter instantiatePresenter(
            Context context,
            AchievementContract.View view,
            EvidencesDataSource dataSource) {

        return new AchievementPresenter(context, view, dataSource);
    }
}