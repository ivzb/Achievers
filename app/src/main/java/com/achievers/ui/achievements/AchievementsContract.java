package com.achievers.ui.achievements;

import android.support.annotation.NonNull;

import com.achievers.ui.base.BasePresenter;
import com.achievers.ui.base.BaseView;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Category;

import java.util.List;

public class AchievementsContract {
    public interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showAchievements(Category category, List<Achievement> achievements);

        void showAchievementDetailsUi(/*int categoryId*/);

        void showAddAchievementUi(int categoryId);
    }

    public interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadAchievements();

        void openAchievementDetails(@NonNull Achievement requestedAchievement);
    }
}