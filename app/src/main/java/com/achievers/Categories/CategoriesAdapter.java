package com.achievers.Categories;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.achievers.BR;
import com.achievers.data.models.Category;
import com.achievers.databinding.CategoryItemBinding;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private List<Category> mCategories;
    private CategoriesItemActionHandler mCategoriesItemActionHandler;
    private CategoriesContract.Presenter mUserActionsListener;
    private Context mContext;

    public CategoriesAdapter(List<Category> categories, CategoriesContract.Presenter userActionsListener) {
        this.mCategories = categories;
        this.mCategoriesItemActionHandler = new CategoriesItemActionHandler(userActionsListener);
        this.mUserActionsListener = userActionsListener;
    }

    public Context getContext() {
        return this.mContext;
    }

    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(this.mContext);
        CategoryItemBinding binding = CategoryItemBinding.inflate(layoutInflater, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Category category = this.mCategories.get(position);

        viewHolder.getBinding().setVariable(BR.category, category);
        viewHolder.getBinding().setVariable(BR.resources, this.mContext.getResources());
        viewHolder.getBinding().setVariable(BR.actionHandler, this.mCategoriesItemActionHandler);
        viewHolder.getBinding().executePendingBindings();
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return this.mCategories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CategoryItemBinding binding;

        public ViewHolder(CategoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public CategoryItemBinding getBinding() {
            return this.binding;
        }
    }
}