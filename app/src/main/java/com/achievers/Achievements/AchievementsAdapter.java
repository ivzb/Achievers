package com.achievers.Achievements;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.achievers.BR;
import com.achievers.Categories.CategoriesContract;
import com.achievers.data.Achievement;
import com.achievers.data.Category;
import com.achievers.databinding.AchievementItemBinding;

import java.util.Collection;
import java.util.List;

public class AchievementsAdapter extends RecyclerView.Adapter<AchievementsAdapter.ViewHolder> {

    private List<Achievement> mAchievements;
    private Category mCategory;
    private AchievementsItemActionHandler mAchievementsItemActionHandler;
    private AchievementsContract.Presenter mUserActionsListener;
    private Context mContext;

    public AchievementsAdapter(List<Achievement> achievements, Category category, AchievementsContract.Presenter userActionsListener) {
        this.mAchievements = achievements;
        this.mCategory = category;
        this.mAchievementsItemActionHandler = new AchievementsItemActionHandler(userActionsListener);
        this.mUserActionsListener = userActionsListener;
    }

    public Context getContext() {
        return this.mContext;
    }

    public Category getCategory() {
        return this.mCategory;
    }

    public void addAchievements(Collection<Achievement> achievements) {
        int start = this.getItemCount();
        this.mAchievements.addAll(achievements);
        this.notifyItemRangeInserted(start, achievements.size());
    }

    @Override
    public AchievementsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(this.mContext);
        AchievementItemBinding binding = AchievementItemBinding.inflate(layoutInflater, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Achievement achievement = this.mAchievements.get(position);

        viewHolder.getBinding().setVariable(BR.achievement, achievement);
        viewHolder.getBinding().setVariable(BR.resources, this.mContext.getResources());
        viewHolder.getBinding().setVariable(BR.actionHandler, this.mAchievementsItemActionHandler);
        viewHolder.getBinding().executePendingBindings();
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return this.mAchievements.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private AchievementItemBinding binding;

        public ViewHolder(AchievementItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public AchievementItemBinding getBinding() {
            return this.binding;
        }
    }
}