package com.achievers.ui.achievements;

import android.databinding.ViewDataBinding;

import com.achievers.data.entities.Achievement;
import com.achievers.ui._base.contracts.BasePresenter;
import com.achievers.ui._base.contracts.BaseView;
import com.achievers.ui._base.contracts.BaseViewModel;
import com.achievers.ui._base.contracts.action_handlers.BaseAdapterActionHandler;
import com.achievers.ui._base.contracts.adapters.BaseAdapter;

import java.util.List;

public class AchievementsContract {

    public interface View<DB extends ViewDataBinding>
            extends BaseView<Presenter, ViewModel, DB>, BaseAdapterActionHandler<Achievement> {

        void setLoadingIndicator(boolean active);

        void showAchievements(List<Achievement> achievements);

        void openAchievementUi(Achievement achievement);

        int getPage();
        void setPage(int page);
    }

    public interface Presenter extends BasePresenter {

        void refresh();

        void loadAchievements(int page);

        void clickAchievement(Achievement achievement);

        void result(int requestCode, int resultCode);
    }

    public interface ViewModel extends BaseViewModel {

        int getPage();
        void setPage(int page);

        BaseAdapter<Achievement> getAdapter();
        void setAdapter(BaseAdapter<Achievement> adapter);
    }
}
