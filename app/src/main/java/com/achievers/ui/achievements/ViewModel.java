package com.achievers.ui.achievements;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.achievers.BR;
import com.achievers.ui.categories.CategoriesContract;
import com.achievers.data.entities.Category;

/**
 * Exposes the data to be used in the {@link CategoriesContract.View}.
 * <p>
 * {@link BaseObservable} implements a listener registration mechanism which is notified when a
 * property changes. This is done by assigning a {@link Bindable} annotation to the property's
 * getter method.
 */
public class ViewModel extends BaseObservable {

    private Adapter mAdapter;
    private Category mCategory;

    private Context mContext;

    public ViewModel(Context context) {
        this.mContext = context;
    }

    @Bindable
    public Adapter getAdapter() {
        return this.mAdapter;
    }

    @Bindable
    public Category getCategory()
    {
        return this.mCategory;
    }

    public void setAdapter(Adapter adapter) {
        this.mAdapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    public void setCategory(Category category) {
        this.mCategory = category;
        notifyPropertyChanged(BR.category);
    }
}