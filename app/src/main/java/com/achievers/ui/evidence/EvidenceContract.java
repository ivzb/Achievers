package com.achievers.ui.evidence;

import android.databinding.Bindable;
import android.databinding.ViewDataBinding;

import com.achievers.data.entities.Evidence;
import com.achievers.ui._base.contracts.BasePresenter;
import com.achievers.ui._base.contracts.BaseView;
import com.achievers.ui._base.contracts.BaseViewModel;
import com.achievers.utils.ui.multimedia.MultimediaType;
import com.achievers.utils.ui.multimedia.MultimediaView;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;

public interface EvidenceContract {

    interface View<DB extends ViewDataBinding> extends BaseView<Presenter, ViewModel, DB> {

        void requestPermissions(String[] permissions, int requestCode);

        void initMultimedia();

        void showMultimediaError();

        void finish();
    }

    interface Presenter extends BasePresenter {

        void requestReadExternalStoragePermission();

        void deliverPermissionsResult(
                int requestCode,
                int[] grantResults);

        void buildMultimedia(
                MultimediaView view,
                MultimediaType type,
                String previewUrl,
                BaseMultimediaPlayer player);
    }

    interface ViewModel extends BaseViewModel {

        @Bindable
        Evidence getEvidence();
        void setEvidence(Evidence evidence);

        @Bindable
        boolean isMultimediaFailed();
        void setMultimediaFailed();
    }
}