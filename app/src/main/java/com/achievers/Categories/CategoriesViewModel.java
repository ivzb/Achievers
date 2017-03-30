package com.achievers.Categories;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;

import com.achievers.R;
import com.achievers.BR;

/**
 * Exposes the data to be used in the {@link CategoriesContract.View}.
 * <p>
 * {@link BaseObservable} implements a listener registration mechanism which is notified when a
 * property changes. This is done by assigning a {@link Bindable} annotation to the property's
 * getter method.
 */
public class CategoriesViewModel extends BaseObservable {

    int mCategoriesListSize = 0;

    private final CategoriesContract.Presenter mPresenter;

    private Context mContext;

    public CategoriesViewModel(Context context, CategoriesContract.Presenter presenter) {
        mContext = context;
        mPresenter = presenter;
    }

    @Bindable
    public String getCurrentFilteringLabel() {
        switch (mPresenter.getFiltering()) {
            case ALL_CATEGORIES:
                return mContext.getResources().getString(R.string.label_all_categories);
        }

        return null;
    }

    @Bindable
    public String getNoCategoriesLabel() {
        switch (mPresenter.getFiltering()) {
            case ALL_CATEGORIES:
                return mContext.getResources().getString(R.string.no_categories);
        }

        return null;
    }

    @Bindable
    public Drawable getNoCategoriesIconRes() {
        switch (mPresenter.getFiltering()) {
            case ALL_CATEGORIES:
                return mContext.getResources().getDrawable(R.drawable.ic_assignment_turned_in_24dp);
        }

        return null;
    }

    @Bindable
    public boolean getCategoriesAddViewVisible() {
        return mPresenter.getFiltering() == CategoriesFilterType.ALL_CATEGORIES;
    }

    @Bindable
    public boolean isNotEmpty() {
        return this.mCategoriesListSize > 0;
    }

    public void setCategoriesListSize(int categoriesListSize) {
        this.mCategoriesListSize = categoriesListSize;
        notifyPropertyChanged(BR.noCategoriesIconRes);
        notifyPropertyChanged(BR.noCategoriesLabel);
        notifyPropertyChanged(BR.currentFilteringLabel);
        notifyPropertyChanged(BR.notEmpty);
        notifyPropertyChanged(BR.categoriesAddViewVisible);
    }
}