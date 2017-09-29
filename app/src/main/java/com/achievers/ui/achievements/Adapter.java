package com.achievers.ui.achievements;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.achievers.BR;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Category;
import com.achievers.databinding.AchievementItemBinding;

import java.util.Collection;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Achievement> mAchievements;
    private Category mCategory;
    private ItemActionHandler mAchievementsItemActionHandler;
    private Contracts.Presenter mUserActionsListener;
    private Context mContext;

    public Adapter(List<Achievement> achievements, Category category, Contracts.Presenter userActionsListener) {
        this.mAchievements = achievements;
        this.mCategory = category;
        this.mAchievementsItemActionHandler = new ItemActionHandler(userActionsListener);
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
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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