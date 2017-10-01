package com.achievers.ui.achievements;

import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Category;
import com.achievers.ui.base.BasePresenter;
import com.achievers.ui.base.BaseView;
import com.achievers.ui.base.BaseViewModel;

import java.util.List;

public class Contracts {

    public interface View extends BaseView<Presenter, ViewModel> {

        void setLoadingIndicator(boolean active);

        void showAchievements(/*Category category, */List<Achievement> achievements);

        void showAchievementDetailsUi(/*int categoryId*/);

        void showAddAchievementUi(int categoryId);
    }

    public interface Presenter extends BasePresenter {

//        void result(int requestCode, int resultCode);

        void loadAchievements(int page);

//        void openAchievementDetails(@NonNull Achievement requestedAchievement);
    }

    public interface ViewModel extends BaseViewModel {

        Adapter getAdapter();
        void setAdapter(Adapter adapter);

        Category getCategory();
        void setCategory(Category category);
    }
}