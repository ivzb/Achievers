package com.achievers.ui.achievements;

import android.databinding.ViewDataBinding;
import android.os.Parcelable;

import com.achievers.data.entities.Achievement;
import com.achievers.ui._base.contracts.BasePresenter;
import com.achievers.ui._base.contracts.BaseView;
import com.achievers.ui._base.contracts.BaseViewModel;

import java.util.List;

public class AchievementsContracts {

    public interface View<DB extends ViewDataBinding>
            extends BaseView<Presenter, ViewModel, DB>, AchievementsActionHandler {

        void setLoadingIndicator(boolean active);

        void showAchievements(List<Achievement> achievements);

        void openAchievementUi(long id);
        void openAddAchievementUi();

        int getPage();
        void setPage(int page);
    }

    public interface Presenter extends BasePresenter {

        void refresh();

        void loadAchievements(int page);

        void clickAchievement(Achievement achievement);
        void clickAddAchievement();

        void result(int requestCode, int resultCode);
    }

    public interface ViewModel extends BaseViewModel {

        int getPage();
        void setPage(int page);

        AchievementsContracts.Adapter getAdapter();
        void setAdapter(AchievementsContracts.Adapter adapter);
    }

    public interface Adapter {

        void add(List<Achievement> achievements);

        Parcelable onSaveInstanceState();
        void onRestoreInstanceState(Parcelable parcelable);
    }
}