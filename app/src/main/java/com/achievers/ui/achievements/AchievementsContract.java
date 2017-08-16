package com.achievers.ui.achievements;

import android.support.annotation.NonNull;

import com.achievers.ui.base.BaseView;
import com.achievers.models.Achievement;
import com.achievers.models.Category;

import java.util.List;

public class AchievementsContract {
    public interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showAchievements(Category category, List<Achievement> achievements);

        void showAchievementDetailsUi(int categoryId);

        void showAddAchievementUi(int categoryId);

        void showLoadingError();

        boolean isActive();
    }

    public interface Presenter {

        void result(int requestCode, int resultCode);

        void loadAchievements(Category category, boolean forceUpdate);

        void openAchievementDetails(@NonNull Achievement requestedAchievement);
    }
}
