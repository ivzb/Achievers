package com.achievers.ui.evidence;

import android.databinding.Bindable;
import android.databinding.ViewDataBinding;

import com.achievers.data.entities.Evidence;
import com.achievers.ui._base.contracts.BasePresenter;
import com.achievers.ui._base.contracts.BaseView;
import com.achievers.ui._base.contracts.BaseViewModel;

public interface EvidenceContract {

    interface View<DB extends ViewDataBinding> extends BaseView<Presenter, ViewModel, DB> {

        void requestPermissions(String[] permissions, int requestCode);

        void initMultimedia();

        void finish();
    }

    interface Presenter extends BasePresenter {

        void requestReadExternalStoragePermission();

        void deliverPermissionsResult(
                int requestCode,
                int[] grantResults);
    }

    interface ViewModel extends BaseViewModel {

        @Bindable
        Evidence getEvidence();
        void setEvidence(Evidence evidence);
    }
}