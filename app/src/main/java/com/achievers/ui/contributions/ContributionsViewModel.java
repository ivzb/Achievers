package com.achievers.ui.contributions;

import com.achievers.data.entities.Contribution;
import com.achievers.ui._base.view_models.EndlessAdapterViewModel;

public class ContributionsViewModel
        extends EndlessAdapterViewModel<Contribution>
        implements ContributionsContract.ViewModel {

    @Override
    public Long getContainerId() {
        return null;
    }
}