package com.achievers.ui.contributions;

import android.databinding.ViewDataBinding;

import com.achievers.data.entities.Contribution;
import com.achievers.ui._base._contracts.presenters.BaseEndlessAdapterPresenter;
import com.achievers.ui._base._contracts.view_models.BaseEndlessAdapterViewModel;
import com.achievers.ui._base._contracts.views.BaseEndlessAdapterView;

public class ContributionsContract {

    public interface View<DB extends ViewDataBinding>
            extends BaseEndlessAdapterView<Contribution, Presenter, ViewModel, DB> {

    }

    public interface Presenter extends BaseEndlessAdapterPresenter<Contribution> {

    }

    public interface ViewModel extends BaseEndlessAdapterViewModel<Contribution> {

    }
}
