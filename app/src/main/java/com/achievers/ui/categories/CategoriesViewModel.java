package com.achievers.ui.categories;

import android.content.Context;
import android.database.Cursor;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import com.achievers.BR;
import com.achievers.R;
import com.achievers.entities.Category;

/**
 * Exposes the data to be used in the {@link CategoriesContract.View}.
 * <p>
 * {@link BaseObservable} implements a listener registration mechanism which is notified when a
 * property changes. This is done by assigning a {@link Bindable} annotation to the property's
 * getter method.
 */
public class CategoriesViewModel extends BaseObservable {

    private boolean mIsLoading = true;
    private int mCategoriesListSize = 0;

    private final CategoriesContract.Presenter mPresenter;
    private CategoriesAdapter mAdapter;
    private Cursor mCursor;
    private Category mParent;

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
            case COMPLETED:
                return mContext.getResources().getString(R.string.label_completed);
            case NOT_COMPLETED:
                return mContext.getResources().getString(R.string.label_not_completed);
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
    public boolean isLoading() {
        return mIsLoading;
    }

    @Bindable
    public Drawable getNoCategoriesIconRes() {
        switch (mPresenter.getFiltering()) {
            case ALL_CATEGORIES:
                Drawable allCategories = ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_assignment_turned_in_24dp, null);

                return allCategories;
        }

        return null;
    }

//    @Bindable
//    public boolean getCategoriesAddViewVisible() {
//        return mPresenter.getFiltering() == CategoriesFilterType.ALL_CATEGORIES;
//    }

    @Bindable
    public CategoriesAdapter getAdapter() {
        return this.mAdapter;
    }

    @Bindable
    public Cursor getCursor() {
        return this.mCursor;
    }

    @Bindable
    public Category getParent() {
        return this.mParent;
    }

    @Bindable
    public boolean isNotEmpty() {
        return this.mCategoriesListSize > 0;
    }

    void setCategoriesListSize(int categoriesListSize) {
        this.mCategoriesListSize = categoriesListSize;
        notifyPropertyChanged(BR.noCategoriesIconRes);
        notifyPropertyChanged(BR.noCategoriesLabel);
        notifyPropertyChanged(BR.currentFilteringLabel);
        notifyPropertyChanged(BR.notEmpty);
//        notifyPropertyChanged(BR.categoriesAddViewVisible);
    }

    void setAdapter(CategoriesAdapter adapter) {
        this.mAdapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    void setCursor(Cursor cursor) {
        this.mCursor = cursor;
        notifyPropertyChanged(BR.cursor);
    }

    void setParent(Category parent) {
        this.mParent = parent;
        notifyPropertyChanged(BR.parent);
    }

    void setLoading(boolean isLoading) {
        this.mIsLoading = isLoading;
        notifyPropertyChanged(BR.loading);
    }
}