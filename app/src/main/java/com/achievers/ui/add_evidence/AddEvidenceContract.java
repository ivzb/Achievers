package com.achievers.ui.add_evidence;

import android.content.Intent;
import android.databinding.Bindable;
import android.databinding.ViewDataBinding;
import android.net.Uri;

import com.achievers.data.entities.Evidence;
import com.achievers.ui._base.contracts.BasePresenter;
import com.achievers.ui._base.contracts.BaseView;
import com.achievers.ui._base.contracts.BaseViewModel;
import com.achievers.ui._base.contracts.action_handlers.BasePictureLoadActionHandler;
import com.achievers.utils.ui.multimedia.MultimediaType;

public class AddEvidenceContract {

    interface View<DB extends ViewDataBinding> extends BaseView<Presenter, ViewModel, DB> {

        void takePicture(Uri uri, int requestCode);
        void takeVideo(int requestCode);

        void showMultimedia(Uri uri);
        void showLoadingMultimedia(boolean loading);

        void upload(Evidence evidence);
        void finish();
    }

    interface Presenter extends BasePresenter {

        void clickTakePicture();
        void clickTakeVideo();

        void deliverMultimedia(int requestCode, int resultCode, Intent data);

        void multimediaLoaded(boolean isSuccessful);

        void saveEvidence(
                String title,
                Uri pictureUri);
    }

    public interface ViewModel extends BaseViewModel {

        MultimediaType getMultimediaType();

        @Bindable
        String getTitle();
        void setTitle(String title);

        @Bindable Uri getMultimediaUri();
        void setMultimediaUri(Uri uri);

        @Bindable boolean isMultimediaLoading();
        void setMultimediaLoading(boolean loaded);
    }

    public interface ActionHandler
            extends BasePictureLoadActionHandler {

    }
}