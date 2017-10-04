package com.achievers.ui.achievements;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.achievers.R;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.data.source.achievements.AchievementsDataSource;
import com.achievers.utils.EndlessRecyclerViewScrollListener;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class Presenter
        implements Contracts.Presenter {

    private final Contracts.View mView;
    private final Context mContext;
    private final AchievementsDataSource mDataSource;
//    private final Contracts.View mView;

    Presenter(
            @NonNull Context context,
            @NonNull AchievementsDataSource dataSource,
            @NonNull Contracts.View view) {

        mView = view;
        mContext = checkNotNull(context, "context cannot be null");
        mDataSource = checkNotNull(dataSource, "achievementsDataSource cannot be null");
//        mView = checkNotNull(view, "achievementsView cannot be null");
    }

//    @Override
//    public void result(int requestCode, int resultCode) {
//        if (AddAchievementActivity.REQUEST_ADD_ACHIEVEMENT == requestCode && Activity.RESULT_OK == resultCode) {
//            mView.showSuccessfulMessage("Your achievement will be uploaded shortly.");
//        }
//    }

    @Override
    public void start() {
        initRecycler();

        int initialPage = 0;
        loadAchievements(initialPage);
    }

    @Override
    public void loadAchievements(final int page) {
        if (!mView.isActive()) return;

        mView.setLoadingIndicator(true);

        mDataSource.loadAchievements(page, new LoadCallback<Achievement>() {
            @Override
            public void onSuccess(List<Achievement> data) {
                if (!mView.isActive()) return;

                mView.setLoadingIndicator(false);
                mView.showAchievements(data);
            }

            @Override
            public void onFailure(String message) {
                if (!mView.isActive()) return;

                mView.setLoadingIndicator(false);
                mView.showErrorMessage(message);
            }
        });
    }

    @Override
    public void clickAchievement(Achievement achievement) {
        if (!mView.isActive()) return;

        if (null == achievement) {
            mView.showErrorMessage(mContext.getString(R.string.missing_achievement));
            return;
        }

        mView.openAchievementUi(achievement);
    }

    @Override
    public void clickAddAchievement() {
        if (!mView.isActive()) return;

        mView.openAddAchievementUi();
    }

    private void initRecycler() {
        if (!mView.isActive()) return;

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);

        mView.initRecycler(
                new Adapter(mView),
                layoutManager,
                new EndlessRecyclerViewScrollListener(layoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                        loadAchievements(page);
                    }
                });
    }
}
