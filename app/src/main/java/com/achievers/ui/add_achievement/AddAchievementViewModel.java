package com.achievers.ui.add_achievement;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Bitmap;
import android.os.Parcelable;

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
    private Bitmap mImage;
    private String mImageUrl;

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

    @Override
    public Bitmap getImage() {
        return mImage;
    }

    @Override
    public void setImage(Bitmap picture) {
        mImage = picture;
    }

    @Override
    public String getImageUrl() {
        return mImageUrl;
    }

    @Override
    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}