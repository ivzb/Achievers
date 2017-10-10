package com.achievers.ui.add_achievement;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.net.Uri;
import android.os.Parcelable;

import com.achievers.BR;
import com.achievers.data.entities.Involvement;
import com.achievers.ui._base.contracts.BaseSelectableAdapter;

public class AddAchievementViewModel
        extends BaseObservable
        implements AddAchievementContract.ViewModel {

    private String mTitle;
    private String mDescription;

    private BaseSelectableAdapter<Involvement> mInvolvementsAdapter;
    private Parcelable mInvolvementsLayoutManagerState;
    private Parcelable mInvolvementsState;

    private Uri mImageUri;
    private Uri mCapturedImageUri;
    private boolean mIsImageLoading;

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
    public String getDescription() {
        return mDescription;
    }

    @Override
    public void setDescription(String description) {
        mDescription = description;
    }

    @Override
    public BaseSelectableAdapter<Involvement> getInvolvementsAdapter() {
        return mInvolvementsAdapter;
    }

    @Override
    public void setInvolvementsAdapter(BaseSelectableAdapter<Involvement> adapter) {
        mInvolvementsAdapter = adapter;
    }

    @Override
    public Parcelable getInvolvementsLayoutManagerState() {
        return mInvolvementsLayoutManagerState;
    }

    @Override
    public void setInvolvementsLayoutManagerState(Parcelable state) {
        mInvolvementsLayoutManagerState = state;
    }

    @Override
    public Parcelable getInvolvementsState() {
        return mInvolvementsState;
    }

    @Override
    public void setInvolvementsState(Parcelable state) {
        mInvolvementsState = state;
    }

    @Bindable
    @Override
    public Uri getCapturedImageUri() {
        return mCapturedImageUri;
    }

    @Override
    public void setCapturedImageUri(Uri imageUri) {
        mCapturedImageUri = imageUri;
    }

    @Bindable
    @Override
    public Uri getImageUri() {
        return mImageUri;
    }

    @Override
    public void setImageUri(Uri imageUri) {
        mImageUri = imageUri;

        notifyPropertyChanged(BR.imageUri);
    }

    @Bindable
    @Override
    public boolean isImageLoading() {
        return mIsImageLoading;
    }

    @Override
    public void setImageLoading(boolean loaded) {
        mIsImageLoading = loaded;
        notifyPropertyChanged(BR.imageLoading);
    }
}