package com.achievers.ui.achievements;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.achievers.BR;
import com.achievers.data.entities.Achievement;
import com.achievers.databinding.AchievementsItemBinding;

import java.util.ArrayList;
import java.util.List;

public class Adapter
        extends RecyclerView.Adapter<Adapter.ViewHolder>
        implements Contracts.Adapter {

    private List<Achievement> mAchievements;
    private ItemActionHandler mAchievementsItemActionHandler;

    public Adapter(Contracts.Presenter presenter) {
        mAchievements = new ArrayList<>();
        mAchievementsItemActionHandler = new ItemActionHandler(presenter);
    }

    @Override
    public void addAchievements(List<Achievement> achievements) {
        int start = getItemCount();
        mAchievements.addAll(achievements);
        notifyItemRangeInserted(start, achievements.size());
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        AchievementsItemBinding binding = AchievementsItemBinding.inflate(layoutInflater, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Achievement achievement = mAchievements.get(position);

        viewHolder.getBinding().setVariable(BR.achievement, achievement);
        viewHolder.getBinding().setVariable(BR.actionHandler, mAchievementsItemActionHandler);
        viewHolder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mAchievements.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private AchievementsItemBinding mBinding;

        ViewHolder(AchievementsItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public AchievementsItemBinding getBinding() {
            return mBinding;
        }
    }
}