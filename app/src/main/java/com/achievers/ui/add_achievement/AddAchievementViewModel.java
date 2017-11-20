package com.achievers.ui.add_achievement;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;

import com.achievers.BR;
import com.achievers.data.entities.Involvement;
import com.achievers.ui._base._contracts.adapters.BaseSelectableAdapter;

public class AddAchievementViewModel
        extends BaseObservable
        implements AddAchievementContract.ViewModel {

    private String mTitle;
    private String mDescription;

    private BaseSelectableAdapter<Involvement> mInvolvementsAdapter;
    private RecyclerView.LayoutManager mInvolvementsLayoutManager;

    private Uri mImageUri;
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

    @Bindable
    @Override
    public BaseSelectableAdapter<Involvement> getInvolvementsAdapter() {
        return mInvolvementsAdapter;
    }

    @Bindable
    @Override
    public RecyclerView.LayoutManager getInvolvementsLayoutManager() {
        return mInvolvementsLayoutManager;
    }

    @Override
    public void setInvolvements(
            BaseSelectableAdapter<Involvement> adapter,
            RecyclerView.LayoutManager layoutManager) {

        mInvolvementsAdapter = adapter;
        mInvolvementsLayoutManager = layoutManager;

        notifyPropertyChanged(BR.involvementsAdapter);
        notifyPropertyChanged(BR.involvementsLayoutManager);
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