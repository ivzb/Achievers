package com.achievers.ui.quests;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.R;
import com.achievers.databinding.QuestsFragBinding;
import com.achievers.ui._base.AbstractFragment;

public class QuestsFragment
        extends AbstractFragment<QuestsContract.Presenter, QuestsContract.ViewModel, QuestsFragBinding>
        implements QuestsContract.View<QuestsFragBinding> {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.quests_frag, container, false);

        mDataBinding = QuestsFragBinding.bind(view);

        if (mViewModel == null) mViewModel = new QuestsViewModel();
        if (mPresenter == null) mPresenter = new QuestsPresenter(this);

        mDataBinding.setViewModel(mViewModel);

        return view;
    }
}