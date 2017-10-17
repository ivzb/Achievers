package com.achievers.ui.evidence;

import android.databinding.ViewDataBinding;
import android.view.Menu;
import android.view.MenuInflater;

import com.achievers.R;
import com.achievers.ui._base.AbstractFragment;

public abstract class EvidenceFragment<DB extends ViewDataBinding>
        extends AbstractFragment<EvidenceContract.Presenter, EvidenceContract.ViewModel, DB>
        implements EvidenceContract.View<DB> {

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.upload_fragment_menu, menu);
    }
}