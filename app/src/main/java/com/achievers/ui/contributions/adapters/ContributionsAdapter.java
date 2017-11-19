package com.achievers.ui.contributions.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.achievers.BR;
import com.achievers.data.entities.AchievementProgress;
import com.achievers.databinding.AchievementsProgressRecyclerItemBinding;
import com.achievers.ui._base.AbstractAdapter;
import com.achievers.ui._base.contracts.action_handlers.BaseAdapterActionHandler;

public class ContributionsAdapter extends AbstractAdapter<AchievementProgress> {

    public ContributionsAdapter(
            Context context,
            BaseAdapterActionHandler<AchievementProgress> actionHandler) {

        super(context, actionHandler);
    }

    @Override
    public ContributionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        AchievementsProgressRecyclerItemBinding binding =
                AchievementsProgressRecyclerItemBinding.inflate(
                        layoutInflater, parent, false);

        return new ViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        AchievementProgress achievementProgress = mEntities.get(position);

        viewHolder.getBinding().setVariable(BR.achievementProgress, achievementProgress);
        viewHolder.getBinding().setVariable(BR.resources, mContext.getResources());
        viewHolder.getBinding().setVariable(BR.actionHandler, mActionHandler);
        viewHolder.getBinding().executePendingBindings();
    }
}