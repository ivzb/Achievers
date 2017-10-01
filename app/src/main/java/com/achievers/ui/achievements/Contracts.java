package com.achievers.ui.achievements;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Category;
import com.achievers.ui.base.BasePresenter;
import com.achievers.ui.base.BaseView;
import com.achievers.ui.base.BaseViewModel;
import com.achievers.utils.EndlessRecyclerViewScrollListener;

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

    }

    public interface Adapter {

        void addAchievements(List<Achievement> achievements);
    }
}