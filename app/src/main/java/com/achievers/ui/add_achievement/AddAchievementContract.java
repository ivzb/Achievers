package com.achievers.ui.add_achievement;

import android.content.Intent;
import android.databinding.Bindable;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;

import com.achievers.data.entities.Involvement;
import com.achievers.ui._base.contracts.BasePresenter;
import com.achievers.ui._base.contracts.BaseSelectableAdapter;
import com.achievers.ui._base.contracts.BaseView;
import com.achievers.ui._base.contracts.BaseViewModel;

import java.util.List;

public class AddAchievementContract {

    interface View<DB extends ViewDataBinding> extends BaseView<Presenter, ViewModel, DB> {

        void showInvolvements(List<Involvement> involvement);

        void takePicture(Uri uri, int requestCode);
        void choosePicture(String type, int requestCode);

        void showPicture(Uri imageUri);

        void finish();
    }

    interface Presenter extends BasePresenter {

        void loadInvolvements();

        void clickTakePicture();
        void clickChoosePicture();
        void clickDiscardPicture();

        void deliverPicture(int requestCode, int resultCode, Intent data);

        void saveAchievement(
                String title,
                String description,
                Uri imageUri,
                Involvement involvement);
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

        @Bindable Uri getImageUri();
        void setImageUri(Uri imageUri);

        @Bindable boolean isImageLoading();
        void setImageLoading(boolean loaded);
    }
}