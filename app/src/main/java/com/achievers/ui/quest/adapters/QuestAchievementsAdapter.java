package com.achievers.ui.quest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.achievers.BR;
import com.achievers.R;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Quest;
import com.achievers.databinding.QuestAchievementsRecyclerItemBinding;
import com.achievers.ui._base._contracts.action_handlers.BaseAdapterActionHandler;
import com.achievers.ui.achievements.adapters.AchievementsAdapter;

public class QuestAchievementsAdapter extends AchievementsAdapter {

    private Context mContext;
    private Quest mQuest;

    public QuestAchievementsAdapter(
            Context context,
            Quest quest,
            BaseAdapterActionHandler<Achievement> actionHandler) {

        super(context, actionHandler);

        mContext = context;
        mQuest = quest;
    }

    @Override
    public AchievementsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        QuestAchievementsRecyclerItemBinding binding = QuestAchievementsRecyclerItemBinding.inflate(layoutInflater, parent, false);

        return new ViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        String achievementId = mEntities.get(position).getId();
        boolean completed = mQuest.getCompleted().contains(achievementId);

        int background = 0;

        if (completed) {
            background = mContext.getResources().getColor(R.color.completedAchievement);
        }

        viewHolder.getBinding().setVariable(BR.completed, completed);
        viewHolder.getBinding().setVariable(BR.background, background);

        super.onBindViewHolder(viewHolder, position);
    }
}