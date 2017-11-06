package com.achievers.ui.add_evidence;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.net.Uri;

import com.achievers.BR;
import com.achievers.utils.ui.multimedia.MultimediaType;

public class AddEvidenceViewModel
        extends BaseObservable
        implements AddEvidenceContract.ViewModel {

    private MultimediaType mMultimediaType;
    private String mTitle;

    private Uri mMultimediaUri;
    private boolean mIsMultimediaLoading;

    AddEvidenceViewModel(MultimediaType multimediaType) {
        mMultimediaType = multimediaType;
    }

    public MultimediaType getMultimediaType() {
        return mMultimediaType;
    }

    @Bindable
    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public void setTitle(String title) {
        mTitle = title;
    }

    @Bindable
    @Override
    public Uri getMultimediaUri() {
        return mMultimediaUri;
    }

    @Override
    public void setMultimediaUri(Uri multimediaUri) {
        mMultimediaUri = multimediaUri;
        notifyPropertyChanged(BR.pictureUri);
    }

    @Bindable
    @Override
    public boolean isMultimediaLoading() {
        return mIsMultimediaLoading;
    }

    @Override
    public void setMultimediaLoading(boolean isLoading) {
        mIsMultimediaLoading = isLoading;
        notifyPropertyChanged(BR.multimediaLoading);
    }
}