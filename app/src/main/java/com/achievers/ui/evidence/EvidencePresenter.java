package com.achievers.ui.evidence;

import android.support.annotation.NonNull;

import com.achievers.data.source.evidences.EvidencesDataSource;
import com.achievers.ui._base.AbstractPresenter;

import static com.achievers.utils.Preconditions.checkNotNull;

public class EvidencePresenter
        extends AbstractPresenter<EvidenceContract.View>
        implements EvidenceContract.Presenter {

    private final EvidencesDataSource mEvidencesDataSource;

    EvidencePresenter(
            @NonNull EvidenceContract.View view,
            EvidencesDataSource evidencesDataSource) {

        mView = checkNotNull(view, "view cannot be null");
        mEvidencesDataSource = checkNotNull(evidencesDataSource, "context cannot be null");
    }

    @Override
    public void start() {

    }
}