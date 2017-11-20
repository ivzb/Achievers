package com.achievers.ui.add_achievement;

import android.content.Intent;
import android.databinding.Bindable;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;

import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Involvement;
import com.achievers.ui._base._contracts.BasePresenter;
import com.achievers.ui._base._contracts.BaseView;
import com.achievers.ui._base._contracts.BaseViewModel;
import com.achievers.ui._base._contracts.action_handlers.BasePictureLoadActionHandler;
import com.achievers.ui._base._contracts.adapters.BaseSelectableAdapter;

import java.util.List;

public class AddAchievementContract {

    interface View<DB extends ViewDataBinding> extends BaseView<Presenter, ViewModel, DB> {

        void showInvolvements(List<Involvement> involvement);

        void takePicture(Uri uri, int requestCode);
        void choosePicture(String type, int requestCode);

        void showPicture(Uri pictureUri);
        void showPictureLoading(boolean loading);

        void upload(Achievement achievement);
        void finish();
    }

    interface Presenter extends BasePresenter {

        void loadInvolvements();

        void clickTakePicture();
        void clickChoosePicture();
        void clickDiscardPicture();

        void deliverPicture(int requestCode, int resultCode, Intent data);

        void pictureLoaded();

        void saveAchievement(
                String title,
                String description,
                Uri pictureUri,
                Involvement involvement,
                int involvementSelectedPosition);
    }

    public interface ViewModel extends BaseViewModel {

        @Bindable String getTitle();
        void setTitle(String title);

        @Bindable String getDescription();
        void setDescription(String description);

        @Bindable BaseSelectableAdapter<Involvement> getInvolvementsAdapter();
        @Bindable RecyclerView.LayoutManager getInvolvementsLayoutManager();
        void setInvolvements(
                BaseSelectableAdapter<Involvement> adapter,
                RecyclerView.LayoutManager layoutManager);

        @Bindable Uri getPictureUri();
        void setPictureUri(Uri pictureUri);

        @Bindable boolean isPictureLoading();
        void setPictureLoading(boolean loaded);
    }

    public interface ActionHandler
            extends BasePictureLoadActionHandler {

    }
}