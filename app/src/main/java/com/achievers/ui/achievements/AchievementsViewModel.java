package com.achievers.ui.achievements;

import com.achievers.data.entities.Achievement;
import com.achievers.ui._base.view_models.EndlessAdapterViewModel;

class AchievementsViewModel
        extends EndlessAdapterViewModel<Achievement>
        implements AchievementsContract.ViewModel {

    @Override
    public String getContainerId() {
        return null;
    }
}