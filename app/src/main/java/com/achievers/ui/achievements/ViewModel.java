package com.achievers.ui.achievements;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.achievers.BR;
import com.achievers.data.entities.Category;
import com.achievers.ui.categories.CategoriesContract;

/**
 * Exposes the data to be used in the {@link CategoriesContract.View}.
 * <p>
 * {@link BaseObservable} implements a listener registration mechanism which is notified when a
 * property changes. This is done by assigning a {@link Bindable} annotation to the property's
 * getter method.
 */
public class ViewModel extends BaseObservable implements Contracts.ViewModel {

    private Adapter mAdapter;
    private Category mCategory;

    private Context mContext;

    public ViewModel(Context context) {
        this.mContext = context;
    }

    @Bindable
    @Override
    public Adapter getAdapter() {
        return this.mAdapter;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        this.mAdapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    @Bindable
    @Override
    public Category getCategory()
    {
        return this.mCategory;
    }

    @Override
    public void setCategory(Category category) {
        this.mCategory = category;
        notifyPropertyChanged(BR.category);
    }
}