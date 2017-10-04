package com.achievers.ui.achievements;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.achievers.data.entities.Achievement;
import com.achievers.ui._base.contracts.BasePresenter;
import com.achievers.ui._base.contracts.BaseView;
import com.achievers.ui._base.contracts.BaseViewModel;

import java.util.List;

public class Contracts {

    public interface View extends BaseView<Presenter, ViewModel>, ActionHandler {

        void initRecycler(
                Contracts.Adapter adapter,
                LinearLayoutManager layoutManager,
                RecyclerView.OnScrollListener scrollListener);

        void setLoadingIndicator(boolean active);

        void showAchievements(/*Category category, */List<Achievement> achievements);

        void openAchievementUi(Achievement achievement);
        void openAddAchievementUi();
    }

    public interface Presenter extends BasePresenter {

//        void result(int requestCode, int resultCode);

        void loadAchievements(int page);

        void clickAchievement(@NonNull Achievement achievement);

        void clickAddAchievement();
    }

    public interface ViewModel extends BaseViewModel {

        Contracts.Adapter getAdapter();
        void setAdapter(Contracts.Adapter adapter);
    }

    public interface Adapter {

        void addAchievements(List<Achievement> achievements);
    }
}