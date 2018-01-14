package com.achievers.ui.quests;

import com.achievers.data.entities.Quest;
import com.achievers.ui._base.view_models.EndlessAdapterViewModel;

class QuestsViewModel
        extends EndlessAdapterViewModel<Quest>
        implements QuestsContract.ViewModel {

    @Override
    public String getContainerId() {
        return null;
    }
}