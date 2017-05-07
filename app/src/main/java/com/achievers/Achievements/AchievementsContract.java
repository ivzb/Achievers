package com.achievers.Achievements;

import android.support.annotation.NonNull;

import com.achievers.BasePresenter;
import com.achievers.BaseView;
import com.achievers.data.Achievement;
import com.achievers.data.Category;

import java.util.List;

public class AchievementsContract {
    public interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showAchievements(Category category, List<Achievement> achievements);

        void showAchievementDetailsUi(int categoryId);

        void showLoadingError();

        boolean isActive();
    }

    public interface Presenter {

        void result(int requestCode, int resultCode);

        void loadAchievements(Category category, boolean forceUpdate);

        void openAchievementDetails(@NonNull Achievement requestedAchievement);
    }
}
