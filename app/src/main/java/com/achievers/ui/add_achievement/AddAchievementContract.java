package com.achievers.ui.add_achievement;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.net.Uri;

import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.File;
import com.achievers.data.entities.Involvement;
import com.achievers.ui._base.contracts.BasePresenter;
import com.achievers.ui._base.contracts.BaseView;
import com.achievers.ui._base.contracts.BaseViewModel;
import com.achievers.ui.add_achievement.Adapters.InvolvementAdapter;

import java.util.List;

public class AddAchievementContract {

    interface View<DB extends ViewDataBinding> extends BaseView<Presenter, ViewModel, DB> {

        void takePicture(Uri uri, int requestCode);
        void choosePicture(String type, int requestCode);

        void showInvolvement(List<Involvement> involvement);
        void showImage(Bitmap bitmap);

        void finish();
    }

    interface Presenter extends BasePresenter {

        void requestPermission(int requestCode, int[] grantResults);
        void resultPermission(int requestCode, int resultCode, Intent data);

        void clickTakePicture(int permission);
        void clickChoosePicture();

        void getInvolvements();

        void uploadImage(Bitmap bitmap, SaveCallback<File> callback);

        void saveAchievement(
                String title,
                String description,
                String imageUrl,
                Involvement involvement);
    }

    public interface ViewModel extends BaseViewModel {

        Achievement getAchievement();
        void setAchievement(Achievement achievement);

        InvolvementAdapter getInvolvementsAdapter();
        void setInvolvementsAdapter(InvolvementAdapter adapter);
    }
}