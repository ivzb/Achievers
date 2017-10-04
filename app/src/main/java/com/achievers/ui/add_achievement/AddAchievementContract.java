package com.achievers.ui.add_achievement;

import android.databinding.Bindable;
import android.graphics.Bitmap;

import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.File;
import com.achievers.data.entities.Involvement;
import com.achievers.ui._base.contracts.BaseViewModel;
import com.achievers.ui.add_achievement.Adapters.InvolvementRecyclerViewAdapter;
import com.achievers.ui._base.contracts.BasePresenter;
import com.achievers.ui._base.contracts.BaseView;

import java.util.List;

public class AddAchievementContract {

    interface View extends BaseView<Presenter, ViewModel> {

        void showInvolvement(List<Involvement> involvement);

        void showInvalidAchievementMessage(final String message);

        void finishActivity();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void saveAchievement(final Achievement achievement);

        boolean validateAchievement(final Achievement achievement);

        void getInvolvements();

        void uploadImage(Bitmap bitmap, SaveCallback<File> callback);
    }

    public interface ViewModel extends BaseViewModel {

        Achievement getAchievement();
        void setAchievement(Achievement achievement);

        InvolvementRecyclerViewAdapter getInvolvementsAdapter();
        void setInvolvementsAdapter(InvolvementRecyclerViewAdapter adapter);
    }
}