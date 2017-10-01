package com.achievers.ui.categories;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.achievers.BR;
import com.achievers.data.entities.Category;
import com.achievers.data.source.categories.CategoriesCursor;
import com.achievers.databinding.CategoryItemBinding;
import com.achievers.utils.CursorUtils;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private CategoriesItemActionHandler mCategoriesItemActionHandler;
    private CategoriesContract.Presenter mUserActionsListener;
    private Context mContext;
    private Cursor mCursor;

    public CategoriesAdapter(CategoriesContract.Presenter userActionsListener) {
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
        mCursor.moveToPosition(position);
        Category category = CategoriesCursor.from(mCursor);

        viewHolder.getBinding().setVariable(BR.category, category);
        viewHolder.getBinding().setVariable(BR.resources, this.mContext.getResources());
        viewHolder.getBinding().setVariable(BR.actionHandler, this.mCategoriesItemActionHandler);
        viewHolder.getBinding().executePendingBindings();
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return CursorUtils.getSize(mCursor);
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
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