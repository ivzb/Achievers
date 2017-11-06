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

    private Uri mImageUri;
    private boolean mIsImageLoading;

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
    public Uri getPictureUri() {
        return mImageUri;
    }

    @Override
    public void setPictureUri(Uri pictureUri) {
        mImageUri = pictureUri;

        notifyPropertyChanged(BR.pictureUri);
    }

    @Bindable
    @Override
    public boolean isPictureLoading() {
        return mIsImageLoading;
    }

    @Override
    public void setPictureLoading(boolean loaded) {
        mIsImageLoading = loaded;
        notifyPropertyChanged(BR.pictureLoading);
    }
}