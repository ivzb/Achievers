package com.achievers.ui.achievements;

import android.app.Activity;
import android.content.Context;

import com.achievers.data.entities.Achievement;
import com.achievers.data.source.achievements.AchievementsDataSource;
import com.achievers.ui._base.EndlessAdapterPresenterTest;
import com.achievers.ui.add_achievement.AddAchievementActivity;
import com.achievers.utils.GeneratorUtils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Random;

import io.bloco.faker.Faker;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class AchievementsPresenterTest
        extends EndlessAdapterPresenterTest<Achievement, AchievementsContract.Presenter, AchievementsContract.View, AchievementsDataSource> {

    @Mock protected Context mContext;
    @Mock protected AchievementsContract.View mView;
    @Mock protected AchievementsDataSource mDataSource;

    private static final int sValidRequestCode = AddAchievementActivity.REQUEST_ADD_ACHIEVEMENT;
    private static final int sValidResultCode = Activity.RESULT_OK;

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public AchievementsContract.View getView() {
        return mView;
    }

    @Override
    public AchievementsDataSource getDataSource() {
        return mDataSource;
    }

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        GeneratorUtils.initialize(new Random(), new Faker());

        mPresenter = new AchievementsPresenter(mContext, mView, mDataSource);
    }

    @Test
    public void result_invalidRequestCode() {
        // act
        mPresenter.result(sValidRequestCode + 1, sValidResultCode);
    }

    @Test
    public void result_invalidResultCode() {
        // act
        mPresenter.result(sValidRequestCode, sValidResultCode + 1);
    }

    @Test
    public void result_shouldCallView() {
        // act
        mPresenter.result(sValidRequestCode, sValidResultCode);

        // assert
        verify(mView).showSuccessfulMessage(any(String.class));
    }

    @Override
    public Achievement instantiateModel(Long id) {
        if (id == null) return new Achievement();
        return new Achievement(id);
    }

    @Override
    public AchievementsContract.Presenter instantiatePresenter(
            Context context,
            AchievementsContract.View view,
            AchievementsDataSource dataSource) {

        return new AchievementsPresenter(context, view, dataSource);
    }
}