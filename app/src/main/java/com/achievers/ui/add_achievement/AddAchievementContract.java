package com.achievers.ui.add_achievement;

import android.content.Intent;
import android.databinding.Bindable;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcelable;

import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.entities.File;
import com.achievers.data.entities.Involvement;
import com.achievers.ui._base.contracts.BasePresenter;
import com.achievers.ui._base.contracts.BaseSelectableAdapter;
import com.achievers.ui._base.contracts.BaseView;
import com.achievers.ui._base.contracts.BaseViewModel;

import java.util.List;

public class AddAchievementContract {

    interface View<DB extends ViewDataBinding> extends BaseView<Presenter, ViewModel, DB> {

        void showInvolvement(List<Involvement> involvement);

        void takePicture(Uri uri, int requestCode);
        void choosePicture(String type, int requestCode);

        void showImage(Bitmap bitmap);
        void setImageUrl(String imageUrl);

        void finish();
    }

    interface Presenter extends BasePresenter {

        void loadInvolvements();

        void clickTakePicture(int targetWidth);
        void clickChoosePicture();

        void resultForPicture(int requestCode, int resultCode, Intent data);

        void saveAchievement(
                String title,
                String description,
                String imageUrl,
                Involvement involvement);
    }

    public interface ViewModel extends BaseViewModel {

        @Bindable
        String getTitle();
        void setTitle(String title);

        @Bindable
        String getDescription();
        void setDescription(String description);

        BaseSelectableAdapter<Involvement> getInvolvementsAdapter();
        void setInvolvementsAdapter(BaseSelectableAdapter<Involvement> adapter);

        Parcelable getInvolvementsLayoutManagerState();
        void setInvolvementsLayoutManagerState(Parcelable state);

        Parcelable getInvolvementsState();
        void setInvolvementsState(Parcelable state);

        Bitmap getImage();
        void setImage(Bitmap picture);

        String getImageUrl();
        void setImageUrl(String imageUrl);
    }
}