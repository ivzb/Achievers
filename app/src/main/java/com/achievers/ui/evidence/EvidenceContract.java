package com.achievers.ui.evidence;

import android.databinding.Bindable;
import android.databinding.ViewDataBinding;

import com.achievers.data.entities.Evidence;
import com.achievers.ui._base.contracts.BasePresenter;
import com.achievers.ui._base.contracts.BaseView;
import com.achievers.ui._base.contracts.BaseViewModel;

public interface EvidenceContract {

    interface View<DB extends ViewDataBinding> extends BaseView<Presenter, ViewModel, DB> {


    }

    interface Presenter extends BasePresenter {


    }

    interface ViewModel extends BaseViewModel {

        @Bindable
        Evidence getEvidence();
        void setEvidence(Evidence evidence);
    }
}