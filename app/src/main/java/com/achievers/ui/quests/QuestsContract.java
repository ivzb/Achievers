package com.achievers.ui.quests;

import android.databinding.ViewDataBinding;

import com.achievers.ui._base.contracts.BasePresenter;
import com.achievers.ui._base.contracts.BaseView;
import com.achievers.ui._base.contracts.BaseViewModel;

public interface QuestsContract {

    interface View<DB extends ViewDataBinding>
            extends BaseView<Presenter, ViewModel, DB> {

    }

    interface Presenter extends BasePresenter {

    }

    interface ViewModel extends BaseViewModel {

    }
}