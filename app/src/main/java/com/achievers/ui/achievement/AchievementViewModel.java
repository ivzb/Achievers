package com.achievers.ui.achievement;

import android.databinding.Bindable;

import com.achievers.BR;
import com.achievers.data.entities.Evidence;
import com.achievers.ui._base._contracts.adapters.BaseMultimediaAdapter;
import com.achievers.ui._base.view_models.EndlessAdapterViewModel;

class AchievementViewModel
        extends EndlessAdapterViewModel<Evidence>
        implements AchievementContract.ViewModel {

    private String mId;

    AchievementViewModel(String id) {
        mId = id;
    }

    @Bindable
    @Override
    public BaseMultimediaAdapter<Evidence> getAdapter() {
        return (BaseMultimediaAdapter<Evidence>) super.getAdapter();
    }

    @Override
    public void setAdapter(BaseMultimediaAdapter<Evidence> adapter) {
        super.setAdapter(adapter);
        notifyPropertyChanged(BR.adapter);
    }

    @Override
    public String getContainerId() {
        return mId;
    }
}