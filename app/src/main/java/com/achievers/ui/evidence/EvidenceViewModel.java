package com.achievers.ui.evidence;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.achievers.BR;
import com.achievers.data.entities.Evidence;

public class EvidenceViewModel
        extends BaseObservable
        implements EvidenceContract.ViewModel {

    private Evidence mEvidence;
    private boolean mIsMultimediaFailed;

    EvidenceViewModel(Evidence evidence) {
        setEvidence(evidence);
    }

    @Bindable
    @Override
    public Evidence getEvidence() {
        return this.mEvidence;
    }

    @Override
    public void setEvidence(Evidence evidence) {
        mEvidence = evidence;
        notifyPropertyChanged(BR.evidence);
    }

    @Bindable
    @Override
    public boolean isMultimediaFailed() {
        return mIsMultimediaFailed;
    }

    @Override
    public void setMultimediaFailed() {
        mIsMultimediaFailed = true;
        notifyPropertyChanged(BR.multimediaFailed);
    }
}