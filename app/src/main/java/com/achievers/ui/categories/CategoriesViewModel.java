package com.achievers.ui.categories;

import android.content.Context;
import android.database.Cursor;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import com.achievers.BR;
import com.achievers.R;
import com.achievers.data.entities.Category;

/**
 * Exposes the data to be used in the {@link CategoriesContract.View}.
 * <p>
 * {@link BaseObservable} implements a listener registration mechanism which is notified when a
 * property changes. This is done by assigning a {@link Bindable} annotation to the property's
 * getter method.
 */
public class CategoriesViewModel
        extends BaseObservable
        implements CategoriesContract.ViewModel {

    private boolean mIsLoading = true;
    private int mCategoriesListSize = 0;

    private CategoriesAdapter mAdapter;
    private Cursor mCursor;
//    private List<Category> mData;
    private Category mParent;

    private Context mContext;

    CategoriesViewModel(Context context) {
        mContext = context;
    }

    @Bindable
    @Override
    public String getNoCategoriesLabel() {
        return mContext.getResources().getString(R.string.no_categories);
    }

    @Bindable
    @Override
    public boolean isLoading() {
        return mIsLoading;
    }

    @Override
    public void setLoading(boolean isLoading) {
        this.mIsLoading = isLoading;
        notifyPropertyChanged(BR.loading);
    }

    @Bindable
    @Override
    public Drawable getNoCategoriesIconRes() {
        return ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_assignment_turned_in_24dp, null);
    }

//    @Bindable
//    public boolean getCategoriesAddViewVisible() {
//        return mPresenter.getFiltering() == CategoriesFilterType.ALL_CATEGORIES;
//    }

    @Bindable
    @Override
    public CategoriesAdapter getAdapter() {
        return this.mAdapter;
    }

    @Override
    public void setAdapter(CategoriesAdapter adapter) {
        this.mAdapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    @Bindable
    @Override
    public Cursor getCursor() {
        return this.mCursor;
    }

    @Override
    public void setCursor(Cursor cursor) {
        this.mCursor = cursor;
        notifyPropertyChanged(BR.cursor);
    }

    @Bindable
    @Override
    public Category getParent() {
        return this.mParent;
    }

    @Override
    public void setParent(Category parent) {
        this.mParent = parent;
        notifyPropertyChanged(BR.parent);
    }

    @Bindable
    @Override
    public boolean isNotEmpty() {
        return this.mCategoriesListSize > 0;
    }

    @Override
    public void setCategoriesListSize(int categoriesListSize) {
        this.mCategoriesListSize = categoriesListSize;
        notifyPropertyChanged(BR.noCategoriesIconRes);
        notifyPropertyChanged(BR.noCategoriesLabel);
        notifyPropertyChanged(BR.notEmpty);
//        notifyPropertyChanged(BR.categoriesAddViewVisible);
    }
}