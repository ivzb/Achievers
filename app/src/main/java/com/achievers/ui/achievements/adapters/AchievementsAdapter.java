package com.achievers.ui.achievements.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.achievers.BR;
import com.achievers.data.entities.Achievement;
import com.achievers.databinding.AchievementsRecyclerItemBinding;
import com.achievers.ui._base._contracts.action_handlers.BaseAdapterActionHandler;
import com.achievers.ui._base.adapters.SimpleActionHandlerAdapter;

public class AchievementsAdapter extends SimpleActionHandlerAdapter<Achievement> {

    public AchievementsAdapter(Context context, BaseAdapterActionHandler<Achievement> actionHandler) {
        super(context, actionHandler);
    }

    @Override
    public AchievementsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        AchievementsRecyclerItemBinding binding = AchievementsRecyclerItemBinding.inflate(layoutInflater, parent, false);

        return new ViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Achievement achievement = mEntities.get(position);

        viewHolder.getBinding().setVariable(BR.achievement, achievement);
        viewHolder.getBinding().setVariable(BR.resources, mContext.getResources());
        viewHolder.getBinding().setVariable(BR.actionHandler, mActionHandler);
        viewHolder.getBinding().executePendingBindings();
    }
}